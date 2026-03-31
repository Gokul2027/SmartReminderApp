package com.smartreminder.app.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u0012\u0010\u001b\u001a\u00020\u00192\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0019H\u0002J\b\u0010!\u001a\u00020\u0019H\u0002J\b\u0010\"\u001a\u00020\u0019H\u0002J\b\u0010#\u001a\u00020\u0019H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006$"}, d2 = {"Lcom/smartreminder/app/ui/AddEditTaskActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/smartreminder/app/databinding/ActivityAddEditTaskBinding;", "editingTaskId", "", "loadedTask", "Lcom/smartreminder/app/data/model/Task;", "mapPickerLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "selectedLatitude", "", "selectedLocationName", "", "selectedLongitude", "viewModel", "Lcom/smartreminder/app/ui/viewmodel/TaskViewModel;", "getViewModel", "()Lcom/smartreminder/app/ui/viewmodel/TaskViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "loadTask", "", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "saveTask", "setupInputs", "setupSpinners", "updateSmartHint", "app_debug"})
public final class AddEditTaskActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.smartreminder.app.databinding.ActivityAddEditTaskBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private int editingTaskId = -1;
    private double selectedLatitude = 0.0;
    private double selectedLongitude = 0.0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String selectedLocationName = "";
    @org.jetbrains.annotations.Nullable()
    private com.smartreminder.app.data.model.Task loadedTask;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> mapPickerLauncher = null;
    
    public AddEditTaskActivity() {
        super();
    }
    
    private final com.smartreminder.app.ui.viewmodel.TaskViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupSpinners() {
    }
    
    private final void setupInputs() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void loadTask() {
    }
    
    private final void saveTask() {
    }
    
    private final void updateSmartHint() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}