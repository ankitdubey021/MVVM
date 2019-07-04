package database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao{

    @Insert
    void insertTask(TaskEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(TaskEntry entry);

    @Delete
    void deleteTask(TaskEntry entry);

    @Query("select * from task_entry order by priority")
    LiveData<List<TaskEntry>> loadAllTask();

    @Query("SELECT * FROM task_entry where id = :taskId")
    LiveData<TaskEntry> getTaskById(int taskId);

}
