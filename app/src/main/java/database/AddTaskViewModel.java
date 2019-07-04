package database;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel{
    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDatabase mdb,int taskId) {
        task=mdb.taskDao().getTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
