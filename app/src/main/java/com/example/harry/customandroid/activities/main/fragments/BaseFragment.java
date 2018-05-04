package com.example.harry.customandroid.activities.main.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harry.customandroid.activities.develop.alarm.AlarmActivity;

import butterknife.ButterKnife;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/5.
 */

public abstract class BaseFragment extends Fragment {

    protected Context context;

    public abstract @LayoutRes int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    protected void start(Class<?> cls) {
        Intent starter = new Intent(context, cls);
        context.startActivity(starter);
    }
}
