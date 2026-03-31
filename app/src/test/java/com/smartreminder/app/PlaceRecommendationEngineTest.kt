package com.smartreminder.app

import com.smartreminder.app.ai.PlaceRecommendationEngine
import org.junit.Assert.assertTrue
import org.junit.Test

class PlaceRecommendationEngineTest {

    @Test
    fun `previous visits improve score`() {
        val withoutHistory = PlaceRecommendationEngine.score(distanceMeters = 400.0, previousVisits = 0)
        val withHistory = PlaceRecommendationEngine.score(distanceMeters = 400.0, previousVisits = 3)

        assertTrue(withHistory > withoutHistory)
    }

    @Test
    fun `closer place scores higher when history is equal`() {
        val closer = PlaceRecommendationEngine.score(distanceMeters = 150.0, previousVisits = 1)
        val farther = PlaceRecommendationEngine.score(distanceMeters = 900.0, previousVisits = 1)

        assertTrue(closer > farther)
    }
}
