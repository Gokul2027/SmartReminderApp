package com.smartreminder.app.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\n0\tJ)\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u000f\u001a\u00020\n\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0012"}, d2 = {"Lcom/smartreminder/app/ai/PlaceRecommendationEngine;", "", "()V", "rankPlaces", "", "Lcom/smartreminder/app/ai/PlaceRecommendationEngine$Recommendation;", "places", "Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "visitCountProvider", "Lkotlin/Function1;", "", "score", "", "distanceMeters", "rating", "previousVisits", "(DLjava/lang/Double;I)D", "Recommendation", "app_debug"})
public final class PlaceRecommendationEngine {
    @org.jetbrains.annotations.NotNull()
    public static final com.smartreminder.app.ai.PlaceRecommendationEngine INSTANCE = null;
    
    private PlaceRecommendationEngine() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.smartreminder.app.ai.PlaceRecommendationEngine.Recommendation> rankPlaces(@org.jetbrains.annotations.NotNull()
    java.util.List<com.smartreminder.app.network.OverpassService.NearbyPlace> places, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.smartreminder.app.network.OverpassService.NearbyPlace, java.lang.Integer> visitCountProvider) {
        return null;
    }
    
    public final double score(double distanceMeters, @org.jetbrains.annotations.Nullable()
    java.lang.Double rating, int previousVisits) {
        return 0.0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/smartreminder/app/ai/PlaceRecommendationEngine$Recommendation;", "", "place", "Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "score", "", "previousVisits", "", "(Lcom/smartreminder/app/network/OverpassService$NearbyPlace;DI)V", "getPlace", "()Lcom/smartreminder/app/network/OverpassService$NearbyPlace;", "getPreviousVisits", "()I", "getScore", "()D", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class Recommendation {
        @org.jetbrains.annotations.NotNull()
        private final com.smartreminder.app.network.OverpassService.NearbyPlace place = null;
        private final double score = 0.0;
        private final int previousVisits = 0;
        
        public Recommendation(@org.jetbrains.annotations.NotNull()
        com.smartreminder.app.network.OverpassService.NearbyPlace place, double score, int previousVisits) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.smartreminder.app.network.OverpassService.NearbyPlace getPlace() {
            return null;
        }
        
        public final double getScore() {
            return 0.0;
        }
        
        public final int getPreviousVisits() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.smartreminder.app.network.OverpassService.NearbyPlace component1() {
            return null;
        }
        
        public final double component2() {
            return 0.0;
        }
        
        public final int component3() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.smartreminder.app.ai.PlaceRecommendationEngine.Recommendation copy(@org.jetbrains.annotations.NotNull()
        com.smartreminder.app.network.OverpassService.NearbyPlace place, double score, int previousVisits) {
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