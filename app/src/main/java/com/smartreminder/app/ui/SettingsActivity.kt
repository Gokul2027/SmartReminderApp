package com.smartreminder.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.smartreminder.app.R
import com.smartreminder.app.databinding.ActivitySettingsBinding
import com.smartreminder.app.ui.viewmodel.TaskViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        renderInsights()

        binding.btnClearHabits.setOnClickListener {
            viewModel.habitTracker.clearAll()
            renderInsights()
            Toast.makeText(this, R.string.habit_data_cleared, Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderInsights() {
        binding.tvHabitInsights.text = viewModel.getHabitSummary()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
