package com.smartreminder.app.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

object OverpassService {

    data class NearbyPlace(
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val type: String,
        val distanceMeters: Double,
        val rating: Double? = null
    )

    private const val overpassUrl = "https://overpass-api.de/api/interpreter"

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    suspend fun findNearby(
        latitude: Double,
        longitude: Double,
        category: String,
        radiusMeters: Int = 1000,
        limit: Int = 6
    ): List<NearbyPlace> = withContext(Dispatchers.IO) {
        val tags = categoryToTags(category)
        val queryBody = buildQuery(tags, latitude, longitude, radiusMeters)

        return@withContext runCatching {
            val request = Request.Builder()
                .url(overpassUrl)
                .post(queryBody.toRequestBody("text/plain".toMediaType()))
                .header("User-Agent", "SmartReminderApp/1.0")
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string().orEmpty()
                if (!response.isSuccessful || body.isBlank()) return@use emptyList()
                parseResponse(body, latitude, longitude).take(limit)
            }
        }.getOrElse {
            Log.e("OverpassService", "Nearby search failed", it)
            emptyList()
        }
    }

    private fun buildQuery(
        tags: List<String>,
        latitude: Double,
        longitude: Double,
        radiusMeters: Int
    ): String {
        val union = tags.joinToString("\n") { tag ->
            val parts = tag.split("=")
            val key = parts[0]
            val value = parts[1]
            """
            node["$key"="$value"](around:$radiusMeters,$latitude,$longitude);
            way["$key"="$value"](around:$radiusMeters,$latitude,$longitude);
            relation["$key"="$value"](around:$radiusMeters,$latitude,$longitude);
            """.trimIndent()
        }

        return """
            [out:json][timeout:12];
            (
            $union
            );
            out center;
        """.trimIndent()
    }

    private fun categoryToTags(category: String): List<String> = when (category) {
        "Grocery" -> listOf("shop=supermarket", "shop=grocery", "shop=convenience")
        "Pharmacy" -> listOf("amenity=pharmacy")
        "Bank" -> listOf("amenity=bank", "amenity=atm")
        "Fuel" -> listOf("amenity=fuel")
        "Food" -> listOf("amenity=restaurant", "amenity=cafe", "amenity=fast_food")
        "Fitness" -> listOf("leisure=fitness_centre", "amenity=gym")
        "Shopping" -> listOf("shop=mall", "shop=department_store", "shop=clothes")
        else -> listOf("amenity=marketplace", "shop=supermarket")
    }

    private fun parseResponse(
        json: String,
        originLatitude: Double,
        originLongitude: Double
    ): List<NearbyPlace> {
        return runCatching {
            val elements = JSONObject(json).getJSONArray("elements")
            val places = mutableListOf<NearbyPlace>()

            for (index in 0 until elements.length()) {
                val element = elements.getJSONObject(index)
                val tags = element.optJSONObject("tags") ?: continue
                val name = tags.optString("name").trim()
                if (name.isBlank()) continue

                val point = when {
                    element.has("lat") && element.has("lon") -> {
                        element.getDouble("lat") to element.getDouble("lon")
                    }
                    element.has("center") -> {
                        val center = element.getJSONObject("center")
                        center.getDouble("lat") to center.getDouble("lon")
                    }
                    else -> null
                } ?: continue

                val type = tags.optString("amenity")
                    .ifBlank { tags.optString("shop") }
                    .ifBlank { tags.optString("leisure") }
                    .ifBlank { "place" }

                places += NearbyPlace(
                    name = name,
                    latitude = point.first,
                    longitude = point.second,
                    type = type,
                    distanceMeters = haversineMeters(
                        originLatitude,
                        originLongitude,
                        point.first,
                        point.second
                    )
                )
            }

            places.distinctBy { "${it.name.lowercase()}_${it.type.lowercase()}_${it.latitude}_${it.longitude}" }
                .sortedBy { it.distanceMeters }
        }.getOrElse {
            Log.e("OverpassService", "Response parsing failed", it)
            emptyList()
        }
    }

    private fun haversineMeters(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Double {
        val earthRadiusMeters = 6_371_000.0
        val deltaLatitude = Math.toRadians(latitude2 - latitude1)
        val deltaLongitude = Math.toRadians(longitude2 - longitude1)

        val a = sin(deltaLatitude / 2).let { it * it } +
            cos(Math.toRadians(latitude1)) * cos(Math.toRadians(latitude2)) *
            sin(deltaLongitude / 2).let { it * it }

        return min(
            earthRadiusMeters * 2 * atan2(sqrt(a), sqrt(1 - a)),
            100_000.0
        )
    }
}
