package com.smartreminder.app.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.smartreminder.app.R
import com.smartreminder.app.databinding.ActivityMainBinding
import com.smartreminder.app.ui.adapter.TaskAdapter
import com.smartreminder.app.ui.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private var openedFromNotification = false

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val hasFineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        if (hasFineLocation && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestBackgroundLocationPermission()
        } else if (!hasFineLocation) {
            showPermissionDialog()
        }
        renderSystemStatus()
    }

    private val backgroundLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        renderSystemStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupTaskList()
        observeViewModel()

        binding.fabAddTask.setOnClickListener {
            startActivity(Intent(this, AddEditTaskActivity::class.java))
        }

        checkAndRequestPermissions()
        viewModel.scheduleGeofenceSync()
        openTaskFromNotificationIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        renderSystemStatus()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        openedFromNotification = false
        openTaskFromNotificationIfNeeded()
    }

    private fun setupTaskList() {
        val adapter = TaskAdapter(
            onClick = { task ->
                startActivity(
                    Intent(this, AddEditTaskActivity::class.java)
                        .putExtra("task_id", task.id)
                )
            },
            onComplete = { viewModel.markComplete(it) },
            onDelete = { task ->
                AlertDialog.Builder(this)
                    .setTitle(R.string.delete_task)
                    .setMessage(getString(R.string.delete_task_message, task.name))
                    .setPositiveButton(R.string.delete) { _, _ -> viewModel.deleteTask(task) }
                    .setNegativeButton(R.string.cancel, null)
                    .show()
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allTasks.observe(this) { tasks ->
            adapter.submitList(tasks)
            binding.tvEmptyState.visibility = if (tasks.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun observeViewModel() {
        viewModel.activeTaskCount.observe(this) { activeCount ->
            supportActionBar?.subtitle = resources.getQuantityString(
                R.plurals.active_reminders,
                activeCount,
                activeCount
            )
        }

        viewModel.statusMessage.observe(this) { message ->
            if (message.isNullOrBlank()) return@observe
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            viewModel.consumeStatusMessage()
        }
    }

    private fun checkAndRequestPermissions() {
        val requiredPermissions = buildList {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (requiredPermissions.isNotEmpty()) {
            permissionLauncher.launch(requiredPermissions.toTypedArray())
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestBackgroundLocationPermission()
        }
    }

    private fun requestBackgroundLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.background_location_title)
            .setMessage(R.string.background_location_message)
            .setPositiveButton(R.string.continue_label) { _, _ ->
                backgroundLocationLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            .setNegativeButton(R.string.not_now, null)
            .show()
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.permission_required)
            .setMessage(R.string.permission_required_message)
            .setPositiveButton(R.string.open_settings) { _, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.parse("package:$packageName")
                })
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun renderSystemStatus() {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationEnabled = isLocationEnabled()
        val message = when {
            !hasFineLocation -> getString(R.string.status_missing_location_permission)
            !locationEnabled -> getString(R.string.status_location_disabled)
            else -> null
        }

        binding.cardStatus.visibility = if (message == null) View.GONE else View.VISIBLE
        binding.tvStatusBanner.text = message.orEmpty()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun openTaskFromNotificationIfNeeded() {
        val taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1 || openedFromNotification) return

        openedFromNotification = true
        startActivity(
            Intent(this, AddEditTaskActivity::class.java)
                .putExtra("task_id", taskId)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
