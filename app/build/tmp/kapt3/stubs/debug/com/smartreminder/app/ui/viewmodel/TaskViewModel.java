package com.smartreminder.app.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\r\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010)\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020\u000eJ\u0006\u0010+\u001a\u00020,J\u0006\u0010-\u001a\u00020,J\u000e\u0010.\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0015J\u001e\u00100\u001a\u00020,2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u00020\u000eJ\u0010\u00105\u001a\u0004\u0018\u00010\u000e2\u0006\u00104\u001a\u00020\u000eJ\u0006\u00106\u001a\u00020\u000eJ\u0016\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u00102\u0006\u00108\u001a\u00020\u0011J\u000e\u00109\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0015J\u000e\u0010:\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0015J\u0006\u0010;\u001a\u00020,J\u000e\u0010<\u001a\u00020,2\u0006\u0010=\u001a\u00020\u000eJ\u000e\u0010>\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0015R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0019\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013R\u000e\u0010#\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0013R\u0019\u0010\'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0013\u00a8\u0006?"}, d2 = {"Lcom/smartreminder/app/ui/viewmodel/TaskViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_editorCompleteEvent", "Landroidx/lifecycle/MutableLiveData;", "", "_nearbyPlaces", "", "Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "_searchResults", "Lcom/smartreminder/app/network/NominatimService$SearchResult;", "_statusMessage", "", "activeTaskCount", "Landroidx/lifecycle/LiveData;", "", "getActiveTaskCount", "()Landroidx/lifecycle/LiveData;", "activeTasks", "Lcom/smartreminder/app/data/model/Task;", "getActiveTasks", "allTasks", "getAllTasks", "editorCompleteEvent", "getEditorCompleteEvent", "geofenceManager", "Lcom/smartreminder/app/location/GeofenceManager;", "habitTracker", "Lcom/smartreminder/app/ai/HabitTracker;", "getHabitTracker", "()Lcom/smartreminder/app/ai/HabitTracker;", "nearbyPlaces", "getNearbyPlaces", "repository", "Lcom/smartreminder/app/data/repository/TaskRepository;", "searchResults", "getSearchResults", "statusMessage", "getStatusMessage", "categorize", "taskName", "consumeEditorCompleteEvent", "", "consumeStatusMessage", "deleteTask", "task", "findNearby", "latitude", "", "longitude", "category", "getHabitSuggestion", "getHabitSummary", "getTaskById", "id", "insertTask", "markComplete", "scheduleGeofenceSync", "searchLocations", "query", "updateTask", "app_debug"})
public final class TaskViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.smartreminder.app.data.repository.TaskRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartreminder.app.location.GeofenceManager geofenceManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.smartreminder.app.ai.HabitTracker habitTracker = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.data.model.Task>> allTasks = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.data.model.Task>> activeTasks = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> activeTaskCount = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.String> statusMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Long> _editorCompleteEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Long> editorCompleteEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.smartreminder.app.network.NominatimService.SearchResult>> _searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.network.NominatimService.SearchResult>> searchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.smartreminder.app.network.OverpassService.NearbyPlace>> _nearbyPlaces = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.network.OverpassService.NearbyPlace>> nearbyPlaces = null;
    
    public TaskViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartreminder.app.ai.HabitTracker getHabitTracker() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.data.model.Task>> getAllTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.data.model.Task>> getActiveTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getActiveTaskCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getStatusMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Long> getEditorCompleteEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.network.NominatimService.SearchResult>> getSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.smartreminder.app.network.OverpassService.NearbyPlace>> getNearbyPlaces() {
        return null;
    }
    
    public final void insertTask(@org.jetbrains.annotations.NotNull()
    com.smartreminder.app.data.model.Task task) {
    }
    
    public final void updateTask(@org.jetbrains.annotations.NotNull()
    com.smartreminder.app.data.model.Task task) {
    }
    
    public final void deleteTask(@org.jetbrains.annotations.NotNull()
    com.smartreminder.app.data.model.Task task) {
    }
    
    public final void markComplete(@org.jetbrains.annotations.NotNull()
    com.smartreminder.app.data.model.Task task) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.smartreminder.app.data.model.Task> getTaskById(int id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String categorize(@org.jetbrains.annotations.NotNull()
    java.lang.String taskName) {
        return null;
    }
    
    public final void searchLocations(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void findNearby(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getHabitSuggestion(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHabitSummary() {
        return null;
    }
    
    public final void scheduleGeofenceSync() {
    }
    
    public final void consumeStatusMessage() {
    }
    
    public final void consumeEditorCompleteEvent() {
    }
}