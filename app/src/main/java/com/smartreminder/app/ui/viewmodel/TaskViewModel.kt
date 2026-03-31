package com.smartreminder.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.smartreminder.app.ai.HabitTracker
import com.smartreminder.app.ai.TaskCategorizer
import com.smartreminder.app.data.db.AppDatabase
import com.smartreminder.app.data.model.Task
import com.smartreminder.app.data.repository.TaskRepository
import com.smartreminder.app.location.GeofenceManager
import com.smartreminder.app.network.NominatimService
import com.smartreminder.app.network.OverpassService
import com.smartreminder.app.worker.GeofenceSyncWorker
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepository(AppDatabase.getDatabase(application).taskDao())
    private val geofenceManager = GeofenceManager(application)
    val habitTracker = HabitTracker(application)

    val allTasks: LiveData<List<Task>> = repository.allTasks
    val activeTasks: LiveData<List<Task>> = repository.activeTasks
    val activeTaskCount: LiveData<Int> = repository.activeTaskCount

    private val _statusMessage = MutableLiveData<String?>()
    val statusMessage: LiveData<String?> = _statusMessage

    private val _editorCompleteEvent = MutableLiveData<Long?>()
    val editorCompleteEvent: LiveData<Long?> = _editorCompleteEvent

    private val _searchResults = MutableLiveData<List<NominatimService.SearchResult>>(emptyList())
    val searchResults: LiveData<List<NominatimService.SearchResult>> = _searchResults

    private val _nearbyPlaces = MutableLiveData<List<OverpassService.NearbyPlace>>(emptyList())
    val nearbyPlaces: LiveData<List<OverpassService.NearbyPlace>> = _nearbyPlaces

    fun insertTask(task: Task) {
        viewModelScope.launch {
            runCatching {
                val newId = repository.insert(task)
                val savedTask = task.copy(id = newId)
                val result = geofenceManager.addOrUpdateGeofence(savedTask)
                GeofenceSyncWorker.enqueue(getApplication())

                _statusMessage.postValue(
                    result.fold(
                        onSuccess = { "Reminder saved successfully." },
                        onFailure = { "Task saved, but geofence setup needs attention." }
                    )
                )
                _editorCompleteEvent.postValue(System.currentTimeMillis())
            }.onFailure {
                _statusMessage.postValue("Unable to save the reminder right now.")
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            runCatching {
                repository.update(task)
                val result = geofenceManager.addOrUpdateGeofence(task)
                GeofenceSyncWorker.enqueue(getApplication())

                _statusMessage.postValue(
                    result.fold(
                        onSuccess = { "Reminder updated successfully." },
                        onFailure = { "Task updated, but geofence refresh failed." }
                    )
                )
                _editorCompleteEvent.postValue(System.currentTimeMillis())
            }.onFailure {
                _statusMessage.postValue("Unable to update the reminder right now.")
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            runCatching {
                geofenceManager.removeGeofence(task.id)
                repository.delete(task)
                GeofenceSyncWorker.enqueue(getApplication())
            }.onSuccess {
                _statusMessage.postValue("Task deleted.")
            }.onFailure {
                _statusMessage.postValue("Unable to delete the task.")
            }
        }
    }

    fun markComplete(task: Task) {
        viewModelScope.launch {
            runCatching {
                repository.markComplete(task.id)
                geofenceManager.removeGeofence(task.id)
                GeofenceSyncWorker.enqueue(getApplication())
                habitTracker.recordTaskCompletion(task.copy(isCompleted = true))
            }.onSuccess {
                _statusMessage.postValue("Task marked as completed.")
            }.onFailure {
                _statusMessage.postValue("Unable to mark the task as completed.")
            }
        }
    }

    fun getTaskById(id: Int): LiveData<Task?> = liveData {
        emit(repository.getById(id))
    }

    fun categorize(taskName: String): String = TaskCategorizer.categorize(taskName)

    fun searchLocations(query: String) {
        viewModelScope.launch {
            val results = NominatimService.search(query)
            _searchResults.postValue(results)
            if (results.isEmpty()) {
                _statusMessage.postValue("No matching locations found.")
            }
        }
    }

    fun findNearby(latitude: Double, longitude: Double, category: String) {
        viewModelScope.launch {
            val places = OverpassService.findNearby(latitude, longitude, category)
            _nearbyPlaces.postValue(places)
            if (places.isEmpty()) {
                _statusMessage.postValue("No nearby places found for $category.")
            }
        }
    }

    fun getHabitSuggestion(category: String): String? = habitTracker.getSuggestion(category)

    fun getHabitSummary(): String = habitTracker.buildSummary()

    fun scheduleGeofenceSync() {
        GeofenceSyncWorker.enqueue(getApplication())
    }

    fun consumeStatusMessage() {
        _statusMessage.value = null
    }

    fun consumeEditorCompleteEvent() {
        _editorCompleteEvent.value = null
    }
}
