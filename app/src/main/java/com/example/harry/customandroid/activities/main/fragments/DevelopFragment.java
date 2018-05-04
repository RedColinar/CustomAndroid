package com.example.harry.customandroid.activities.main.fragments;

import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.develop.alarm.AlarmActivity;

import butterknife.OnClick;

public class DevelopFragment extends BaseFragment {

    public static DevelopFragment newInstance() {
        return new DevelopFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_develop;
    }

    @OnClick({R.id.main_develop_alarm})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_develop_alarm:
                start(AlarmActivity.class);
                break;
        }
    }
}
