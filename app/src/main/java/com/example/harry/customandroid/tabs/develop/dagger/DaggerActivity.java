package com.example.harry.customandroid.tabs.develop.dagger;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DaggerActivity extends BaseActivity {

    @Inject
    DaggerPresenter presenter;
    @Inject
    Student s1;
    @Inject
    Student s2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dagger;
    }

    @Override
    public int getTitleId() {
        return R.string.dagger_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    public void gotoSecond(View view) {
        Log.d(TAG, "gotoSecond: --- student: " + s1.toString());
    }

    public void requestHttp(View view) {
        Log.d(TAG, "requestHttp: --- student: " + s2.toString());
    }
}