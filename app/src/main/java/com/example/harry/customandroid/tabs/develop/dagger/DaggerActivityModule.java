package com.example.harry.customandroid.tabs.develop.dagger;

import android.app.Activity;

import com.example.harry.customandroid.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = DaggerActivitySubcomponent.class)
public abstract class DaggerActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(DaggerActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(DaggerActivitySubcomponent.Builder builder);
}
