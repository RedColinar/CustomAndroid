package com.example.harry.customandroid.tabs.develop.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

import com.example.harry.customandroid.R;

/**
 * created by panqiang at 18-12-28
 */
public class UploadTask implements Runnable {

    private UploadService service;
    private NotificationManager notificationManager;
    private Notification.Builder notificationBuilder;
    private Handler mainThreadHandler;

    private int NOTIFICATIN_ID;

    public void init(UploadService service) {
        this.service = service;
        this.mainThreadHandler = new Handler(service.getMainLooper());

        createNotification();
    }

    private void createNotification() {
        NOTIFICATIN_ID = (int) System.currentTimeMillis();
        notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new Notification.Builder(service.getApplicationContext());
        notificationBuilder.setContentTitle("Title")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("要显示的内容")
                .setProgress(100, 0 , false)
                .setWhen(System.currentTimeMillis());
        Notification notification = notificationBuilder.build();
        notificationManager.notify(NOTIFICATIN_ID, notification);
        service.startForeground(NOTIFICATIN_ID, notification);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            onProgress(100, i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        onFinish();
    }

    private void onProgress(int max, int current) {
        notificationBuilder
                .setProgress(max, current, false)
                .setContentText("上传" + current + "%");
        notificationManager.notify(NOTIFICATIN_ID, notificationBuilder.build());
    }

    private void onFinish() {
        notificationBuilder
                .setProgress(0, 0, true)
                .setContentText("下载结束");
        notificationManager.notify(NOTIFICATIN_ID, notificationBuilder.build());
        notificationManager.cancel(NOTIFICATIN_ID);
    }
}
