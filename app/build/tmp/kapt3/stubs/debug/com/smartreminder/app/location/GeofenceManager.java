package com.smartreminder.app.location;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0087@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ*\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u001dH\u0087@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"Lcom/smartreminder/app/location/GeofenceManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "geofencePendingIntent", "Landroid/app/PendingIntent;", "getGeofencePendingIntent", "()Landroid/app/PendingIntent;", "geofencePendingIntent$delegate", "Lkotlin/Lazy;", "geofencingClient", "Lcom/google/android/gms/location/GeofencingClient;", "addOrUpdateGeofence", "Lkotlin/Result;", "", "task", "Lcom/smartreminder/app/data/model/Task;", "addOrUpdateGeofence-gIAlu-s", "(Lcom/smartreminder/app/data/model/Task;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildGeofence", "Lcom/google/android/gms/location/Geofence;", "removeGeofence", "taskId", "", "removeGeofence-gIAlu-s", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncAll", "tasks", "", "syncAll-gIAlu-s", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GeofenceManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.GeofencingClient geofencingClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy geofencePendingIntent$delegate = null;
    
    public GeofenceManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.app.PendingIntent getGeofencePendingIntent() {
        return null;
    }
    
    private final com.google.android.gms.location.Geofence buildGeofence(com.smartreminder.app.data.model.Task task) {
        return null;
    }
}