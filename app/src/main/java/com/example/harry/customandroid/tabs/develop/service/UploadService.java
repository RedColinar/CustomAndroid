package com.example.harry.customandroid.tabs.develop.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by panqiang at 18-12-27
 */
public class UploadService extends Service {

    private static final String TAG = "UploadService";
    protected static final String PARAM_TASK_CLASS = "taskClass";

    private final UploadBinder mBinder = new UploadBinder();
    private static Handler mHandler;

    private ThreadPoolExecutor uploadThreadPool;
    public static int UPLOAD_POOL_SIZE = 1;
    public static int KEEP_ALIVE_TIME_IN_SECONDS = 0;
    private static final int QUEUE_CAPACITY = 20;
    private final BlockingQueue<Runnable> uploadTasksQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    private static final Map<String, UploadTask> uploadTaskMap = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public static UploadService from(IBinder binder) {
        return ((UploadBinder) binder).getService();
    }

    private class UploadBinder extends Binder {
        private UploadService getService() {
            return UploadService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        Log.d(TAG, "onCreate: ");

        uploadThreadPool = new ThreadPoolExecutor(
                UPLOAD_POOL_SIZE,
                UPLOAD_POOL_SIZE,
                KEEP_ALIVE_TIME_IN_SECONDS,
                TimeUnit.SECONDS,
                uploadTasksQueue);
        uploadThreadPool.setRejectedExecutionHandler((r, executor) -> {
            Toast.makeText(this, "上传任务已达最大数量, 请稍后再试！", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        uploadThreadPool.execute(() -> {
            Runnable task = getTask(intent);
            if (task != null) {
                task.run();
            }
        });
        return START_STICKY;
    }

    private Runnable getTask(Intent intent) {
        String taskClass = intent.getStringExtra(PARAM_TASK_CLASS);
        if (TextUtils.isEmpty(taskClass)) {
            return null;
        }

        UploadTask uploadTask = null;

        try {
            Class<?> task = Class.forName(taskClass);
            if (UploadTask.class.isAssignableFrom(task)) {
                uploadTask = UploadTask.class.cast(task.newInstance());
                uploadTask.init(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uploadTask;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        Log.d(TAG, "onDestroy");
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        uploadThreadPool.shutdown();
    }
}
