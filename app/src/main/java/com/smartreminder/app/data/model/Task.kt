package com.smartreminder.app.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    indices = [
        Index(value = ["isCompleted"]),
        Index(value = ["category"])
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Float,
    val reminderType: String,
    val isCompleted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis(),
    val locationName: String = ""
)
