package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskEntry.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK=new Object();
    private static final String DATABASE_NAME="tododb";
    private static AppDatabase instance=null;

    public static AppDatabase getInstance(Context context){
        if(instance==null){
            synchronized (LOCK){
                instance= Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }

    public abstract TaskDao taskDao();

}
