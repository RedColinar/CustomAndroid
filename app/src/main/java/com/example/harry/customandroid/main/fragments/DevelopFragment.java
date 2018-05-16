package com.example.harry.customandroid.main.fragments;

import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.tabs.develop.alarm.AlarmActivity;
import com.example.harry.customandroid.base.BaseFragment;
import com.example.harry.customandroid.tabs.develop.circleImage.CircleActivity;
import com.example.harry.customandroid.tabs.develop.dagger.DaggerActivity;
import com.example.harry.customandroid.tabs.develop.timeline.TimelineActivity;

import butterknife.OnClick;

public class DevelopFragment extends BaseFragment {

    public static DevelopFragment newInstance() {
        return new DevelopFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_develop;
    }

    @OnClick({
            R.id.main_develop_alarm,
            R.id.main_develop_timeline,
            R.id.main_develop_circleImage,
            R.id.main_develop_dagger
    })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_develop_alarm:
                start(AlarmActivity.class);
                break;
            case R.id.main_develop_timeline:
                start(TimelineActivity.class);
                break;
            case R.id.main_develop_circleImage:
                start(CircleActivity.class);
                break;
            case R.id.main_develop_dagger:
                start(DaggerActivity.class);
        }
    }
}
