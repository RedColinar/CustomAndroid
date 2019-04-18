package com.example.harry.customandroid.tabs.develop.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by panqiang at 2019/4/18
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = {StudentModule.class})
    abstract DaggerActivity contributeDaggerActivity();
}
