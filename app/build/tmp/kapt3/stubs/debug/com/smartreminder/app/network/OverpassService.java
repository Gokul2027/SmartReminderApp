package com.smartreminder.app.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0007\u001a\u00020\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J@\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0013\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0014J(\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J&\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00120\t2\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/smartreminder/app/network/OverpassService;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "overpassUrl", "", "buildQuery", "tags", "", "latitude", "", "longitude", "radiusMeters", "", "categoryToTags", "category", "findNearby", "Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "limit", "(DDLjava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "haversineMeters", "latitude1", "longitude1", "latitude2", "longitude2", "parseResponse", "json", "originLatitude", "originLongitude", "NearbyPlace", "app_debug"})
public final class OverpassService {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String overpassUrl = "https://overpass-api.de/api/interpreter";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.smartreminder.app.network.OverpassService INSTANCE = null;
    
    private OverpassService() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object findNearby(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String category, int radiusMeters, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.smartreminder.app.network.OverpassService.NearbyPlace>> $completion) {
        return null;
    }
    
    private final java.lang.String buildQuery(java.util.List<java.lang.String> tags, double latitude, double longitude, int radiusMeters) {
        return null;
    }
    
    private final java.util.List<java.lang.String> categoryToTags(java.lang.String category) {
        return null;
    }
    
    private final java.util.List<com.smartreminder.app.network.OverpassService.NearbyPlace> parseResponse(java.lang.String json, double originLatitude, double originLongitude) {
        return null;
    }
    
    private final double haversineMeters(double latitude1, double longitude1, double latitude2, double longitude2) {
        return 0.0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012JL\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010\u00a8\u0006#"}, d2 = {"Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "", "name", "", "latitude", "", "longitude", "type", "distanceMeters", "rating", "(Ljava/lang/String;DDLjava/lang/String;DLjava/lang/Double;)V", "getDistanceMeters", "()D", "getLatitude", "getLongitude", "getName", "()Ljava/lang/String;", "getRating", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getType", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/String;DDLjava/lang/String;DLjava/lang/Double;)Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class NearbyPlace {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        private final double latitude = 0.0;
        private final double longitude = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String type = null;
        private final double distanceMeters = 0.0;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Double rating = null;
        
        public NearbyPlace(@org.jetbrains.annotations.NotNull()
        java.lang.String name, double latitude, double longitude, @org.jetbrains.annotations.NotNull()
        java.lang.String type, double distanceMeters, @org.jetbrains.annotations.Nullable()
        java.lang.Double rating) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        public final double getLatitude() {
            return 0.0;
        }
        
        public final double getLongitude() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getType() {
            return null;
        }
        
        public final double getDistanceMeters() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double getRating() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final double component3() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        public final double component5() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Double component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.smartreminder.app.network.OverpassService.NearbyPlace copy(@org.jetbrains.annotations.NotNull()
        java.lang.String name, double latitude, double longitude, @org.jetbrains.annotations.NotNull()
        java.lang.String type, double distanceMeters, @org.jetbrains.annotations.Nullable()
        java.lang.Double rating) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}