package database;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private AppDatabase mDatabase;
    private int taskId;

    public AddTaskViewModelFactory(AppDatabase mDatabase, int taskId) {
        this.mDatabase = mDatabase;
        this.taskId = taskId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(mDatabase,taskId);
    }
}
