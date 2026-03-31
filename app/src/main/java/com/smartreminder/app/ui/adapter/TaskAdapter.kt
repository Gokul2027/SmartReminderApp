package com.smartreminder.app.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartreminder.app.data.model.Task
import com.smartreminder.app.databinding.ItemTaskBinding

class TaskAdapter(
    private val onClick: (Task) -> Unit,
    private val onComplete: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.tvTaskName.text = task.name
            binding.tvCategory.text = task.category
            binding.tvReminderType.text =
                if (task.reminderType.equals("EXIT", true)) "On exit" else "On enter"
            binding.tvLocation.text = task.locationName.ifBlank {
                "${"%.5f".format(task.latitude)}, ${"%.5f".format(task.longitude)}"
            }

            binding.cbCompleted.setOnCheckedChangeListener(null)
            binding.cbCompleted.isChecked = task.isCompleted
            binding.cbCompleted.isEnabled = !task.isCompleted
            binding.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked && !task.isCompleted) {
                    onComplete(task)
                }
            }

            binding.tvTaskName.paintFlags = if (task.isCompleted) {
                binding.tvTaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvTaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            binding.badgeCategory.setCardBackgroundColor(categoryColor(task.category))
            binding.root.setOnClickListener { onClick(task) }
            binding.btnDelete.setOnClickListener { onDelete(task) }
        }

        private fun categoryColor(category: String): Int = when (category) {
            "Grocery" -> 0xFF2E7D32.toInt()
            "Pharmacy" -> 0xFFC2185B.toInt()
            "Bank" -> 0xFF1565C0.toInt()
            "Work" -> 0xFF6A1B9A.toInt()
            "Fuel" -> 0xFFE65100.toInt()
            "Food" -> 0xFFEF6C00.toInt()
            "Fitness" -> 0xFF00838F.toInt()
            "Shopping" -> 0xFF5D4037.toInt()
            else -> 0xFF546E7A.toInt()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
    }
}
