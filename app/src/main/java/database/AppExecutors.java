package database;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static AppExecutors instance;
    private final Executor diskIO,mainThread,networkIO;
    private final static Object LOCK =new Object();
    private AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if(instance==null){
            synchronized (LOCK){
                instance=new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor()
                );
            }
        }
        return instance;
    }
    public Executor diskIO(){return diskIO;}
    public Executor mainThread(){return mainThread;}
    public Executor networkIO(){return networkIO;}


    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler =new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
         mainThreadHandler.post(command);
        }
    }
}
