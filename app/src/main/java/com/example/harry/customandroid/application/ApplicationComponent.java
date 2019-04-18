package com.example.harry.customandroid.application;

import com.example.harry.customandroid.tabs.develop.dagger.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by panqiang at 2019/4/18
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityModule.class
})
public interface ApplicationComponent {
    void inject(MyApplication application);
}
