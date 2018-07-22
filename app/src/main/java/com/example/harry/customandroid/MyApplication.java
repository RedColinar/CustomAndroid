package com.example.harry.customandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.harry.customandroid.tabs.develop.dagger.DaggerMyAppComponent;
import com.example.harry.customandroid.tabs.develop.greendao.entity.DaoMaster;
import com.example.harry.customandroid.tabs.develop.greendao.entity.DaoSession;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/8.
 */

public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    private static Context app;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        Stetho.initializeWithDefaults(this);
        Fresco.initialize(this);
        DaggerMyAppComponent.create().inject(this);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    public static Context getAppContext() {
        return app;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
