package com.smartreminder.app.network

import android.util.Log
import com.smartreminder.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NominatimService {

    data class SearchResult(
        val displayName: String,
        val latitude: Double,
        val longitude: Double
    )

    private val client = OkHttpClient()
    private const val baseUrl = "https://nominatim.openstreetmap.org"
    private val userAgent = "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME} (student-project)"

    suspend fun search(query: String): List<SearchResult> = withContext(Dispatchers.IO) {
        val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.name())
        val url = "$baseUrl/search?q=$encodedQuery&format=json&limit=5&addressdetails=1"

        return@withContext runCatching {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", userAgent)
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string().orEmpty()
                if (!response.isSuccessful || body.isBlank()) return@use emptyList()
                val items = JSONArray(body)
                buildList {
                    for (index in 0 until items.length()) {
                        val item = items.getJSONObject(index)
                        add(
                            SearchResult(
                                displayName = item.optString("display_name"),
                                latitude = item.optString("lat").toDouble(),
                                longitude = item.optString("lon").toDouble()
                            )
                        )
                    }
                }
            }
        }.getOrElse {
            Log.e("NominatimService", "Search failed", it)
            emptyList()
        }
    }

    suspend fun reverseGeocode(latitude: Double, longitude: Double): String =
        withContext(Dispatchers.IO) {
            val url = "$baseUrl/reverse?lat=$latitude&lon=$longitude&format=json"

            return@withContext runCatching {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", userAgent)
                    .build()

                client.newCall(request).execute().use { response ->
                    val body = response.body?.string().orEmpty()
                    if (!response.isSuccessful || body.isBlank()) return@use "$latitude, $longitude"

                    val root = JSONObject(body)
                    val address = root.optJSONObject("address")
                    if (address == null) {
                        root.optString("display_name", "$latitude, $longitude")
                    } else {
                        listOfNotNull(
                            address.optString("road").takeIf { it.isNotBlank() },
                            address.optString("suburb").takeIf { it.isNotBlank() },
                            address.optString("city").takeIf { it.isNotBlank() }
                                ?: address.optString("town").takeIf { it.isNotBlank() }
                                ?: address.optString("village").takeIf { it.isNotBlank() }
                        ).joinToString(", ").ifBlank {
                            root.optString("display_name", "$latitude, $longitude")
                        }
                    }
                }
            }.getOrElse {
                Log.e("NominatimService", "Reverse geocode failed", it)
                "$latitude, $longitude"
            }
        }
}
