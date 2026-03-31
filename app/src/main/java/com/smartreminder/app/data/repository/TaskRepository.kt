package com.smartreminder.app.data.repository

import androidx.lifecycle.LiveData
import com.smartreminder.app.data.db.TaskDao
import com.smartreminder.app.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    val activeTasks: LiveData<List<Task>> = taskDao.getActiveTasks()
    val activeTaskCount: LiveData<Int> = taskDao.getActiveTaskCount()

    suspend fun insert(task: Task): Int = taskDao.insertTask(task).toInt()

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun getById(id: Int): Task? = taskDao.getTaskById(id)

    suspend fun markComplete(taskId: Int) {
        taskDao.updateTaskCompletion(taskId, true)
    }

    suspend fun getActiveTaskSnapshot(): List<Task> = taskDao.getActiveTasksSnapshot()
}
