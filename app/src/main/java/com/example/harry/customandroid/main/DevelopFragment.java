package com.example.harry.customandroid.main;

import android.util.SparseArray;
import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.tabs.develop.alarm.AlarmActivity;
import com.example.harry.customandroid.base.BaseFragment;
import com.example.harry.customandroid.tabs.develop.circleImage.CircleActivity;
import com.example.harry.customandroid.tabs.develop.circleProgress.CircleProgressActivity;
import com.example.harry.customandroid.tabs.develop.customView.CustomViewActivity;
import com.example.harry.customandroid.tabs.develop.dagger.DaggerActivity;
import com.example.harry.customandroid.tabs.develop.greendao.GreenDaoActivity;
import com.example.harry.customandroid.tabs.develop.kotlin.KotlinActivity;
import com.example.harry.customandroid.tabs.develop.timeline.TimelineActivity;
import com.example.harry.customandroid.tabs.develop.wave.WaveActivity;
import com.example.harry.customandroid.tabs.develop.xmlParse.XmlParseActivity;

import butterknife.OnClick;

public class DevelopFragment extends BaseFragment {
    public final SparseArray<Class<?>> activities = new SparseArray<>();

    {
        activities.put(R.id.main_develop_alarm, AlarmActivity.class);
        activities.put(R.id.main_develop_timeline, TimelineActivity.class);
        activities.put(R.id.main_develop_dagger, DaggerActivity.class);
        activities.put(R.id.main_develop_custom_view, CustomViewActivity.class);
        activities.put(R.id.main_develop_circleImage, CircleActivity.class);
        activities.put(R.id.main_develop_xml_parse, XmlParseActivity.class);
        activities.put(R.id.main_develop_wave, WaveActivity.class);
        activities.put(R.id.main_develop_greendao, GreenDaoActivity.class);
        activities.put(R.id.main_develop_circleProgress, CircleProgressActivity.class);
        activities.put(R.id.main_develop_kotlin, KotlinActivity.class);
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
            R.id.main_develop_circleImage,
            R.id.main_develop_xml_parse,
            R.id.main_develop_wave,
            R.id.main_develop_greendao,
            R.id.main_develop_circleProgress,
            R.id.main_develop_kotlin
    })
    void onClick(View view) {
        Class<?> activityClass = activities.get(view.getId());
        if (activityClass != null) {
            start(activityClass);
        }
    }
}
