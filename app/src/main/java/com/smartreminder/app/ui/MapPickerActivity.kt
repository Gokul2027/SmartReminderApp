package com.smartreminder.app.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.smartreminder.app.R
import com.smartreminder.app.ai.HabitTracker
import com.smartreminder.app.ai.PlaceRecommendationEngine
import com.smartreminder.app.databinding.ActivityMapPickerBinding
import com.smartreminder.app.network.NominatimService
import com.smartreminder.app.ui.adapter.SearchResultAdapter
import com.smartreminder.app.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Polygon

class MapPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapPickerBinding
    private lateinit var mapView: MapView
    private lateinit var searchResultAdapter: SearchResultAdapter

    private val viewModel: TaskViewModel by viewModels()
    private lateinit var habitTracker: HabitTracker

    private var selectedPoint: GeoPoint? = null
    private var selectedLocationName = ""
    private var currentMarker: Marker? = null
    private var currentCircle: Polygon? = null
    private var selectedCategory = "General"
    private var selectedRadiusMeters = 150f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.pick_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        selectedCategory = intent.getStringExtra("selected_category").orEmpty().ifBlank { "General" }
        selectedRadiusMeters = intent.getFloatExtra("current_radius", 150f)
        habitTracker = HabitTracker(this)

        setupMap()
        setupSearch()
        setupObservers()
        setupActions()
    }

    private fun setupMap() {
        mapView = binding.osmMapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(5.0)

        val existingLatitude = intent.getDoubleExtra("current_lat", 0.0)
        val existingLongitude = intent.getDoubleExtra("current_lng", 0.0)
        if (existingLatitude != 0.0 || existingLongitude != 0.0) {
            val existingPoint = GeoPoint(existingLatitude, existingLongitude)
            placeMarker(existingPoint)
            mapView.controller.setZoom(16.0)
            mapView.controller.setCenter(existingPoint)
            lifecycleScope.launch {
                selectedLocationName = NominatimService.reverseGeocode(
                    existingLatitude,
                    existingLongitude
                )
                binding.tvLocationName.text = selectedLocationName
            }
        } else {
            moveToCurrentLocation()
        }

        mapView.overlays.add(object : Overlay() {
            override fun onSingleTapConfirmed(
                event: android.view.MotionEvent,
                mapView: MapView
            ): Boolean {
                val point = mapView.projection.fromPixels(event.x.toInt(), event.y.toInt()) as GeoPoint
                placeMarker(point)
                resolvePlaceName(point)
                return true
            }
        })
    }

    private fun setupSearch() {
        searchResultAdapter = SearchResultAdapter(::onSearchResultSelected)
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = searchResultAdapter

        binding.btnSearch.setOnClickListener { searchLocations() }
        binding.etSearch.setOnEditorActionListener { _, actionId, keyEvent ->
            val shouldSearch = actionId == EditorInfo.IME_ACTION_SEARCH ||
                keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER
            if (shouldSearch) {
                searchLocations()
            }
            shouldSearch
        }
    }

    private fun setupObservers() {
        viewModel.searchResults.observe(this) { results ->
            searchResultAdapter.submitList(results)
            binding.rvSearchResults.visibility = if (results.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.nearbyPlaces.observe(this) { places ->
            binding.progressNearby.visibility = View.GONE
            val recommendations = PlaceRecommendationEngine.rankPlaces(places) { place ->
                habitTracker.getPlaceVisitCount(selectedCategory, place.name)
            }

            binding.tvNearbyTitle.text = getString(R.string.recommended_places_title, selectedCategory)
            binding.tvNearbyPlaces.text = if (recommendations.isEmpty()) {
                getString(R.string.no_nearby_places_found)
            } else {
                recommendations.take(3).joinToString("\n") { recommendation ->
                    val distance = recommendation.place.distanceMeters.toInt()
                    val visitHint = if (recommendation.previousVisits > 0) {
                        " - visited ${recommendation.previousVisits}x"
                    } else {
                        ""
                    }
                    "${recommendation.place.name} (${distance}m)$visitHint"
                }
            }
        }

        viewModel.statusMessage.observe(this) { message ->
            if (message.isNullOrBlank()) return@observe
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            viewModel.consumeStatusMessage()
        }
    }

    private fun setupActions() {
        binding.btnConfirmLocation.setOnClickListener {
            val point = selectedPoint ?: run {
                Toast.makeText(this, R.string.tap_map_to_select, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("latitude", point.latitude)
                putExtra("longitude", point.longitude)
                putExtra("location_name", selectedLocationName)
            })
            finish()
        }
    }

    private fun onSearchResultSelected(result: NominatimService.SearchResult) {
        val point = GeoPoint(result.latitude, result.longitude)
        selectedLocationName = result.displayName
        binding.tvLocationName.text = result.displayName
        binding.rvSearchResults.visibility = View.GONE
        mapView.controller.setZoom(16.0)
        mapView.controller.setCenter(point)
        placeMarker(point)
    }

    private fun searchLocations() {
        val query = binding.etSearch.text?.toString()?.trim().orEmpty()
        if (query.length < 2) {
            Toast.makeText(this, R.string.search_minimum_length, Toast.LENGTH_SHORT).show()
            return
        }
        if (!isOnline()) {
            Toast.makeText(this, R.string.no_internet_message, Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.searchLocations(query)
    }

    private fun placeMarker(point: GeoPoint) {
        selectedPoint = point

        currentMarker?.let { mapView.overlays.remove(it) }
        currentCircle?.let { mapView.overlays.remove(it) }

        currentCircle = Polygon().apply {
            points = Polygon.pointsAsCircle(point, selectedRadiusMeters.toDouble())
            fillPaint.color = Color.argb(45, 25, 118, 210)
            outlinePaint.color = Color.rgb(25, 118, 210)
            outlinePaint.strokeWidth = 4f
        }

        currentMarker = Marker(mapView).apply {
            position = point
            title = getString(R.string.reminder_location)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }

        mapView.overlays.add(currentCircle)
        mapView.overlays.add(currentMarker)
        mapView.invalidate()
        binding.btnConfirmLocation.isEnabled = true
        loadNearbyPlaces(point)
    }

    private fun loadNearbyPlaces(point: GeoPoint) {
        if (!isOnline()) {
            binding.progressNearby.visibility = View.GONE
            binding.tvNearbyPlaces.text = getString(R.string.no_internet_message)
            return
        }

        binding.progressNearby.visibility = View.VISIBLE
        binding.tvNearbyTitle.text = getString(R.string.recommended_places_title, selectedCategory)
        binding.tvNearbyPlaces.text = getString(R.string.loading_nearby_places)
        viewModel.findNearby(point.latitude, point.longitude, selectedCategory)
    }

    private fun resolvePlaceName(point: GeoPoint) {
        lifecycleScope.launch {
            selectedLocationName = NominatimService.reverseGeocode(point.latitude, point.longitude)
            binding.tvLocationName.text = selectedLocationName.ifBlank {
                "${point.latitude}, ${point.longitude}"
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun moveToCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(this)
                .lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val point = GeoPoint(location.latitude, location.longitude)
                        mapView.controller.setCenter(point)
                        mapView.controller.setZoom(15.0)
                    } else {
                        mapView.controller.setCenter(GeoPoint(20.5937, 78.9629))
                        mapView.controller.setZoom(4.5)
                    }
                }
        } else {
            mapView.controller.setCenter(GeoPoint(20.5937, 78.9629))
            mapView.controller.setZoom(4.5)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        mapView.onDetach()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
