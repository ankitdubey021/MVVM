package database;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<TaskEntry>> list;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database=AppDatabase.getInstance(this.getApplication());
        list=database.taskDao().loadAllTask();
    }

    public LiveData<List<TaskEntry>> getTask(){
        return list;
    }
}
