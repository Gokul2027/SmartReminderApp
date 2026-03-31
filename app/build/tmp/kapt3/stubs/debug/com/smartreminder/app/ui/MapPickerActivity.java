package com.smartreminder.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0013H\u0002J\b\u0010!\u001a\u00020\u001fH\u0003J\u0012\u0010\"\u001a\u00020\u001f2\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\b\u0010%\u001a\u00020\u001fH\u0014J\b\u0010&\u001a\u00020\u001fH\u0014J\b\u0010\'\u001a\u00020\u001fH\u0014J\u0010\u0010(\u001a\u00020\u001f2\u0006\u0010)\u001a\u00020*H\u0002J\b\u0010+\u001a\u00020\u001dH\u0016J\u0010\u0010,\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0013H\u0002J\u0010\u0010-\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0013H\u0002J\b\u0010.\u001a\u00020\u001fH\u0002J\b\u0010/\u001a\u00020\u001fH\u0002J\b\u00100\u001a\u00020\u001fH\u0002J\b\u00101\u001a\u00020\u001fH\u0002J\b\u00102\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019\u00a8\u00063"}, d2 = {"Lcom/smartreminder/app/ui/MapPickerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/smartreminder/app/databinding/ActivityMapPickerBinding;", "currentCircle", "Lorg/osmdroid/views/overlay/Polygon;", "currentMarker", "Lorg/osmdroid/views/overlay/Marker;", "habitTracker", "Lcom/smartreminder/app/ai/HabitTracker;", "mapView", "Lorg/osmdroid/views/MapView;", "searchResultAdapter", "Lcom/smartreminder/app/ui/adapter/SearchResultAdapter;", "selectedCategory", "", "selectedLocationName", "selectedPoint", "Lorg/osmdroid/util/GeoPoint;", "selectedRadiusMeters", "", "viewModel", "Lcom/smartreminder/app/ui/viewmodel/TaskViewModel;", "getViewModel", "()Lcom/smartreminder/app/ui/viewmodel/TaskViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "isOnline", "", "loadNearbyPlaces", "", "point", "moveToCurrentLocation", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "onSearchResultSelected", "result", "Lcom/smartreminder/app/network/NominatimService$SearchResult;", "onSupportNavigateUp", "placeMarker", "resolvePlaceName", "searchLocations", "setupActions", "setupMap", "setupObservers", "setupSearch", "app_debug"})
public final class MapPickerActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.smartreminder.app.databinding.ActivityMapPickerBinding binding;
    private org.osmdroid.views.MapView mapView;
    private com.smartreminder.app.ui.adapter.SearchResultAdapter searchResultAdapter;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.smartreminder.app.ai.HabitTracker habitTracker;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.util.GeoPoint selectedPoint;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String selectedLocationName = "";
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Marker currentMarker;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Polygon currentCircle;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String selectedCategory = "General";
    private float selectedRadiusMeters = 150.0F;
    
    public MapPickerActivity() {
        super();
    }
    
    private final com.smartreminder.app.ui.viewmodel.TaskViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupMap() {
    }
    
    private final void setupSearch() {
    }
    
    private final void setupObservers() {
    }
    
    private final void setupActions() {
    }
    
    private final void onSearchResultSelected(com.smartreminder.app.network.NominatimService.SearchResult result) {
    }
    
    private final void searchLocations() {
    }
    
    private final void placeMarker(org.osmdroid.util.GeoPoint point) {
    }
    
    private final void loadNearbyPlaces(org.osmdroid.util.GeoPoint point) {
    }
    
    private final void resolvePlaceName(org.osmdroid.util.GeoPoint point) {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void moveToCurrentLocation() {
    }
    
    private final boolean isOnline() {
        return false;
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}