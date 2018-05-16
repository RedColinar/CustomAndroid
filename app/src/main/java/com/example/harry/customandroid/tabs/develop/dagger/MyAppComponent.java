package com.example.harry.customandroid.tabs.develop.dagger;

import com.example.harry.customandroid.MyApplication;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        DaggerActivityModule.class
})
public interface MyAppComponent {
    void inject(MyApplication application);
}
