package com.smartreminder.app.ai

import com.smartreminder.app.network.OverpassService
import kotlin.math.min

object PlaceRecommendationEngine {

    data class Recommendation(
        val place: OverpassService.NearbyPlace,
        val score: Double,
        val previousVisits: Int
    )

    fun rankPlaces(
        places: List<OverpassService.NearbyPlace>,
        visitCountProvider: (OverpassService.NearbyPlace) -> Int
    ): List<Recommendation> {
        return places.map { place ->
            val previousVisits = visitCountProvider(place)
            Recommendation(
                place = place,
                score = score(place.distanceMeters, place.rating, previousVisits),
                previousVisits = previousVisits
            )
        }.sortedByDescending { it.score }
    }

    fun score(
        distanceMeters: Double,
        rating: Double? = null,
        previousVisits: Int = 0
    ): Double {
        val distanceScore = 100.0 - min(distanceMeters, 2000.0) / 20.0
        val ratingScore = ((rating ?: 0.0).coerceIn(0.0, 5.0) / 5.0) * 25.0
        val habitScore = min(previousVisits, 5) * 10.0
        return distanceScore + ratingScore + habitScore
    }
}
