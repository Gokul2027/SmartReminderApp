package com.smartreminder.app.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.smartreminder.app.R
import com.smartreminder.app.ai.TaskCategorizer
import com.smartreminder.app.data.model.Task
import com.smartreminder.app.databinding.ActivityAddEditTaskBinding
import com.smartreminder.app.ui.viewmodel.TaskViewModel

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private val viewModel: TaskViewModel by viewModels()

    private var editingTaskId = -1
    private var selectedLatitude = 0.0
    private var selectedLongitude = 0.0
    private var selectedLocationName = ""
    private var loadedTask: Task? = null

    private val mapPickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
        val data = result.data ?: return@registerForActivityResult

        selectedLatitude = data.getDoubleExtra("latitude", 0.0)
        selectedLongitude = data.getDoubleExtra("longitude", 0.0)
        selectedLocationName = data.getStringExtra("location_name").orEmpty()

        binding.tvSelectedLocation.text = selectedLocationName.ifBlank {
            "${"%.5f".format(selectedLatitude)}, ${"%.5f".format(selectedLongitude)}"
        }
        binding.btnPickLocation.setText(R.string.change_location)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editingTaskId = intent.getIntExtra("task_id", -1)
        val isEditing = editingTaskId != -1

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(
            if (isEditing) R.string.edit_task else R.string.add_task
        )
        binding.btnSave.setText(if (isEditing) R.string.update_task else R.string.save_task)

        setupSpinners()
        setupInputs()
        observeViewModel()

        binding.btnPickLocation.setOnClickListener {
            mapPickerLauncher.launch(
                Intent(this, MapPickerActivity::class.java).apply {
                    putExtra("selected_category", binding.spinnerCategory.selectedItem.toString())
                    putExtra("current_radius", binding.etRadius.text.toString().toFloatOrNull() ?: 150f)
                    putExtra("current_lat", selectedLatitude)
                    putExtra("current_lng", selectedLongitude)
                }
            )
        }

        binding.btnSave.setOnClickListener { saveTask() }

        if (isEditing) {
            loadTask()
        } else {
            updateSmartHint()
        }
    }

    private fun setupSpinners() {
        val categories = TaskCategorizer.getAllCategories()
        val categoryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        val reminderTypeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("ENTER", "EXIT")
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spinnerCategory.adapter = categoryAdapter
        binding.spinnerReminderType.adapter = reminderTypeAdapter

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateSmartHint()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setupInputs() {
        binding.etTaskName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(text: Editable?) {
                val taskName = text?.toString().orEmpty()
                if (taskName.length >= 3) {
                    val category = viewModel.categorize(taskName)
                    val categoryIndex = TaskCategorizer.getAllCategories().indexOf(category)
                    if (categoryIndex >= 0) {
                        binding.spinnerCategory.setSelection(categoryIndex)
                    }
                }
                updateSmartHint()
            }
        })
    }

    private fun observeViewModel() {
        viewModel.statusMessage.observe(this) { message ->
            if (message.isNullOrBlank()) return@observe
            binding.btnSave.isEnabled = true
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            viewModel.consumeStatusMessage()
        }

        viewModel.editorCompleteEvent.observe(this) { event ->
            if (event == null) return@observe
            viewModel.consumeEditorCompleteEvent()
            finish()
        }
    }

    private fun loadTask() {
        viewModel.getTaskById(editingTaskId).observe(this) { task ->
            task ?: return@observe
            loadedTask = task
            binding.etTaskName.setText(task.name)
            binding.etRadius.setText(task.radius.toInt().toString())
            selectedLatitude = task.latitude
            selectedLongitude = task.longitude
            selectedLocationName = task.locationName
            binding.tvSelectedLocation.text = task.locationName.ifBlank {
                "${"%.5f".format(task.latitude)}, ${"%.5f".format(task.longitude)}"
            }
            binding.btnPickLocation.setText(R.string.change_location)

            val categories = TaskCategorizer.getAllCategories()
            val categoryIndex = categories.indexOf(task.category)
            if (categoryIndex >= 0) binding.spinnerCategory.setSelection(categoryIndex)

            val reminderTypes = listOf("ENTER", "EXIT")
            val reminderIndex = reminderTypes.indexOf(task.reminderType)
            if (reminderIndex >= 0) binding.spinnerReminderType.setSelection(reminderIndex)

            updateSmartHint()
        }
    }

    private fun saveTask() {
        val taskName = binding.etTaskName.text?.toString()?.trim().orEmpty()
        if (taskName.isBlank()) {
            binding.etTaskName.error = getString(R.string.required_field)
            return
        }

        if (selectedLatitude == 0.0 && selectedLongitude == 0.0) {
            Toast.makeText(this, R.string.select_location_first, Toast.LENGTH_SHORT).show()
            return
        }

        val radius = binding.etRadius.text?.toString()?.toFloatOrNull()
        if (radius == null || radius < 50f || radius > 5000f) {
            binding.etRadius.error = getString(R.string.radius_validation_message)
            return
        }

        val task = Task(
            id = if (editingTaskId == -1) 0 else editingTaskId,
            name = taskName,
            category = binding.spinnerCategory.selectedItem.toString(),
            latitude = selectedLatitude,
            longitude = selectedLongitude,
            radius = radius,
            reminderType = binding.spinnerReminderType.selectedItem.toString(),
            isCompleted = loadedTask?.isCompleted ?: false,
            timestamp = loadedTask?.timestamp ?: System.currentTimeMillis(),
            locationName = selectedLocationName
        )

        binding.btnSave.isEnabled = false
        if (editingTaskId == -1) {
            viewModel.insertTask(task)
        } else {
            viewModel.updateTask(task)
        }
    }

    private fun updateSmartHint() {
        val typedCategory = viewModel.categorize(binding.etTaskName.text?.toString().orEmpty())
        val selectedCategory = binding.spinnerCategory.selectedItem?.toString().orEmpty()
        val habitSuggestion = viewModel.getHabitSuggestion(selectedCategory)

        binding.tvSmartHint.text = buildString {
            if (binding.etTaskName.text?.isNotBlank() == true) {
                append(getString(R.string.auto_category_hint, typedCategory))
            } else {
                append(getString(R.string.smart_hint_default))
            }

            if (!habitSuggestion.isNullOrBlank()) {
                append("\n")
                append(habitSuggestion)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
