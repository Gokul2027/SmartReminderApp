package com.smartreminder.app.ai

import android.content.Context
import android.content.SharedPreferences
import com.smartreminder.app.data.model.Task
import java.util.Calendar

class HabitTracker(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("habit_tracker", Context.MODE_PRIVATE)

    fun recordTaskCompletion(task: Task) {
        recordCategoryVisit(task.category)
        recordTimeWindow(task.category)
        if (task.locationName.isNotBlank()) {
            recordPlaceVisit(task.category, task.locationName)
        }
    }

    fun getVisitCount(category: String): Int = prefs.getInt(categoryKey(category), 0)

    fun getPlaceVisitCount(category: String, placeName: String): Int {
        return prefs.getInt(placeKey(category, placeName), 0)
    }

    fun getFrequentCategories(): List<Pair<String, Int>> {
        return TaskCategorizer.getAllCategories()
            .map { category -> category to getVisitCount(category) }
            .filter { (_, count) -> count > 0 }
            .sortedByDescending { (_, count) -> count }
    }

    fun getPreferredTimeWindow(category: String): String? {
        val buckets = listOf("morning", "afternoon", "evening", "night")
            .associateWith { bucket -> prefs.getInt(timeKey(category, bucket), 0) }
        val winner = buckets.maxByOrNull { it.value } ?: return null
        return winner.key.takeIf { winner.value >= 2 }
    }

    fun isCurrentTimeWindow(window: String): Boolean = window == currentTimeWindow()

    fun getSuggestion(category: String): String? {
        val visits = getVisitCount(category)
        if (visits < 3) return null

        val timeHint = getPreferredTimeWindow(category)
            ?.let { " Most of your $category reminders happen in the $it." }
            .orEmpty()

        return "You often complete $category tasks near similar places.$timeHint"
    }

    fun buildSummary(): String {
        val frequent = getFrequentCategories()
        if (frequent.isEmpty()) {
            return "No habit insights yet.\nComplete a few reminders to let the app learn patterns."
        }

        return frequent.joinToString("\n\n") { (category, count) ->
            val timeWindow = getPreferredTimeWindow(category)
            buildString {
                append("$category: $count completed reminder")
                if (count != 1) append("s")
                if (!timeWindow.isNullOrBlank()) {
                    append("\nBest time window: ")
                    append(timeWindow.replaceFirstChar { it.uppercase() })
                }
            }
        }
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }

    private fun recordCategoryVisit(category: String) {
        val nextValue = getVisitCount(category) + 1
        prefs.edit()
            .putInt(categoryKey(category), nextValue)
            .putLong("last_${normalize(category)}", System.currentTimeMillis())
            .apply()
    }

    private fun recordPlaceVisit(category: String, placeName: String) {
        val key = placeKey(category, placeName)
        prefs.edit().putInt(key, prefs.getInt(key, 0) + 1).apply()
    }

    private fun recordTimeWindow(category: String) {
        val bucket = currentTimeWindow()
        val key = timeKey(category, bucket)
        prefs.edit().putInt(key, prefs.getInt(key, 0) + 1).apply()
    }

    private fun currentTimeWindow(): String {
        return when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> "morning"
            in 12..16 -> "afternoon"
            in 17..21 -> "evening"
            else -> "night"
        }
    }

    private fun categoryKey(category: String): String = "visit_${normalize(category)}"

    private fun placeKey(category: String, placeName: String): String {
        return "place_${normalize(category)}_${normalize(placeName)}"
    }

    private fun timeKey(category: String, bucket: String): String {
        return "time_${normalize(category)}_${normalize(bucket)}"
    }

    private fun normalize(value: String): String {
        return value.lowercase()
            .replace(Regex("[^a-z0-9]+"), "_")
            .trim('_')
    }
}
