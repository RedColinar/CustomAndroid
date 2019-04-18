package com.example.harry.customandroid.tabs.develop.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by panqiang at 2019/4/18
 */
@Module
public class StudentModule {

    @Provides
    Student providerStudent() {
        return new Student(10);
    }
}
