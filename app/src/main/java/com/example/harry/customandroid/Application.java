package com.example.harry.customandroid;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/8.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
