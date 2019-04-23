package com.example.harry.customandroid.main.fragments;

import android.util.SparseArray;
import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.tabs.develop.alarm.AlarmActivity;
import com.example.harry.customandroid.base.BaseFragment;
import com.example.harry.customandroid.tabs.develop.calendar.CalendarActivity;
import com.example.harry.customandroid.tabs.develop.changeDeskIcon.ChangeDeskIconActivity;
import com.example.harry.customandroid.tabs.develop.circleImage.CircleActivity;
import com.example.harry.customandroid.tabs.develop.circleProgress.CircleProgressActivity;
import com.example.harry.customandroid.tabs.develop.coorinatorLayout.CoordinatorLayoutActivity;
import com.example.harry.customandroid.tabs.develop.customView.CustomViewActivity;
import com.example.harry.customandroid.tabs.develop.dagger.DaggerActivity;
import com.example.harry.customandroid.tabs.develop.greendao.GreenDaoActivity;
import com.example.harry.customandroid.tabs.develop.pictureToAscii.PictureToAsciiActivity;
import com.example.harry.customandroid.tabs.develop.ping.PingActivity;
import com.example.harry.customandroid.tabs.develop.service.ServiceDemoActivity;
import com.example.harry.customandroid.tabs.develop.tagViewLayout.CustomLayoutActivity;
import com.example.harry.customandroid.tabs.develop.tagViewLayout.TagViewLayoutActivity;
import com.example.harry.customandroid.tabs.develop.timeline.TimelineActivity;
import com.example.harry.customandroid.tabs.develop.wave.WaveActivity;
import com.example.harry.customandroid.tabs.develop.xmlParse.XmlParseActivity;

public class DevelopFragment extends BaseFragment implements View.OnClickListener {
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
        activities.put(R.id.main_develop_usage_of_ping, PingActivity.class);
        activities.put(R.id.main_develop_picture_to_ascii, PictureToAsciiActivity.class);
        activities.put(R.id.main_develop_change_desk_icon, ChangeDeskIconActivity.class);
        activities.put(R.id.main_develop_tag_view, TagViewLayoutActivity.class);
        activities.put(R.id.main_develop_service_usage, ServiceDemoActivity.class);
        activities.put(R.id.main_develop_calendar, CalendarActivity.class);
        activities.put(R.id.main_develop_coordinator, CoordinatorLayoutActivity.class);
        activities.put(R.id.main_develop_tag_layout, CustomLayoutActivity.class);
    }

    @Override
    public void onClick(View view) {
        Class<?> activityClass = activities.get(view.getId());
        if (activityClass != null) {
            start(activityClass);
        }
    }

    public static DevelopFragment newInstance() {
        return new DevelopFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_develop;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getView() == null) return;
        for (int i = 0; i < activities.size(); i++) {
            View view = getView().findViewById(activities.keyAt(i));
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }
}
