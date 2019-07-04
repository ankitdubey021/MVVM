package com.ebiztechnocrats.mvvm;

import adapter.TaskAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import database.AppDatabase;
import database.AppExecutors;
import database.MainViewModel;
import database.TaskEntry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView rv;
    TaskAdapter adapter;
    List<TaskEntry> list;
    AppDatabase mdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        adapter=new TaskAdapter(list,id->{
            Intent i=new Intent(MainActivity.this,AddTaskActivity.class);
            i.putExtra(AddTaskActivity.EXTRA_TASK_ID,id);
            startActivity(i);
        });
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                TaskEntry entry=adapter.getTaskList().get(position);
                AppExecutors.getInstance().diskIO().execute(()->{
                    mdb.taskDao().deleteTask(entry);
                });
            }
        }).attachToRecyclerView(rv);

        mdb=AppDatabase.getInstance(getApplicationContext());
        retreiveTask();
    }


    public void add(View v){
        startActivity(new Intent(MainActivity.this,AddTaskActivity.class));
    }


    private void retreiveTask() {

        MainViewModel model=new MainViewModel(getApplication());
        model.getTask().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> list) {
                adapter.addTaskList(list);
            }
        });


        /*LiveData<List<TaskEntry>> list=mdb.taskDao().loadAllTask();
        list.observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> list) {
                adapter.addTaskList(list);
            }
        });*/

        /*//LiveData automatically runs on UI thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<TaskEntry> list=mdb.taskDao().loadAllTask();
                runOnUiThread(()->adapter.addTaskList(list));
            }
        });*/
    }


}
