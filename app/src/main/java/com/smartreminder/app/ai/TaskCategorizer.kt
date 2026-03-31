package com.smartreminder.app.ai

object TaskCategorizer {

    private val categoryKeywords = linkedMapOf(
        "Grocery" to listOf(
            "grocery", "milk", "bread", "eggs", "vegetable", "vegetables", "fruit",
            "fruits", "rice", "snack", "supermarket", "mart", "groceries"
        ),
        "Pharmacy" to listOf(
            "medicine", "medicines", "tablet", "capsule", "syrup", "pharmacy",
            "prescription", "vitamin", "bandage", "ointment", "doctor"
        ),
        "Bank" to listOf(
            "bank", "atm", "cash", "withdraw", "deposit", "transfer", "loan",
            "credit", "debit", "payment"
        ),
        "Work" to listOf(
            "work", "office", "meeting", "client", "report", "email", "presentation",
            "deadline", "project", "submit", "document", "sign"
        ),
        "Fuel" to listOf(
            "fuel", "petrol", "diesel", "gas", "tank", "pump", "station", "refuel"
        ),
        "Food" to listOf(
            "food", "eat", "meal", "breakfast", "lunch", "dinner", "restaurant",
            "cafe", "coffee", "tea", "snacks"
        ),
        "Fitness" to listOf(
            "gym", "workout", "exercise", "run", "jog", "walk", "yoga",
            "fitness", "training", "swimming", "cycle"
        ),
        "Shopping" to listOf(
            "buy", "purchase", "shop", "mall", "store", "clothes", "gift",
            "shoes", "electronics", "shopping"
        )
    )

    fun categorize(taskName: String): String {
        val normalized = taskName.lowercase().trim()
        if (normalized.isEmpty()) return "General"

        val winner = categoryKeywords
            .mapValues { (_, keywords) ->
                keywords.sumOf { keyword ->
                    when {
                        normalized.contains("$keyword ") -> 2
                        normalized.contains(keyword) -> 1
                        else -> 0
                    }
                }
            }
            .maxByOrNull { it.value }

        return if (winner != null && winner.value > 0) winner.key else "General"
    }

    fun getAllCategories(): List<String> = listOf("General") + categoryKeywords.keys.toList()
}
