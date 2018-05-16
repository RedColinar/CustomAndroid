package com.example.harry.customandroid.tabs.develop.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {
        AndroidInjectionModule.class,
        DaggerActivitySubcomponent.SubModule.class
})
public interface DaggerActivitySubcomponent extends AndroidInjector<DaggerActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DaggerActivity> {
    }

    @Module
    class SubModule {

        @Provides
        String provideName() {
            return DaggerActivity.class.getName();
        }

        @Provides
        Student provideStudent() {
            return new Student();
        }

        @Provides
        SharedPreferences provideSp(DaggerActivity activity) {
            return activity.getSharedPreferences("def", Context.MODE_PRIVATE);
        }
    }
}
