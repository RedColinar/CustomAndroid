package com.example.harry.customandroid.main.fragments;

import android.util.SparseArray;
import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.tabs.develop.alarm.AlarmActivity;
import com.example.harry.customandroid.base.BaseFragment;
import com.example.harry.customandroid.tabs.develop.circleImage.CircleActivity;
import com.example.harry.customandroid.tabs.develop.customView.CustomViewActivity;
import com.example.harry.customandroid.tabs.develop.dagger.DaggerActivity;
import com.example.harry.customandroid.tabs.develop.timeline.TimelineActivity;

import butterknife.OnClick;

public class DevelopFragment extends BaseFragment {
    public final SparseArray<Class<?>> activities = new SparseArray<>();

    {
        activities.put(R.id.main_develop_alarm, AlarmActivity.class);
        activities.put(R.id.main_develop_timeline, TimelineActivity.class);
        activities.put(R.id.main_develop_dagger, DaggerActivity.class);
        activities.put(R.id.main_develop_custom_view, CustomViewActivity.class);
        activities.put(R.id.main_develop_circleImage, CircleActivity.class);
    }

    public static DevelopFragment newInstance() {
        return new DevelopFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_develop;
    }

    // 注解里面的参数不能是变量
    @OnClick({
            R.id.main_develop_alarm,
            R.id.main_develop_timeline,
            R.id.main_develop_dagger,
            R.id.main_develop_custom_view,
            R.id.main_develop_circleImage
    })
    void onClick(View view) {
        Class<?> activityClass = activities.get(view.getId());
        if (activityClass != null) {
            start(activityClass);
        }
    }
}
