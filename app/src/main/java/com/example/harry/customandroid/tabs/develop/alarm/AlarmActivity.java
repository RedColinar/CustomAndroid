package com.example.harry.customandroid.tabs.develop.alarm;

import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import butterknife.OnClick;

public class AlarmActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    public int getTitleId() {
        return R.string.alarm_title;
    }

    @OnClick({R.id.confirm_alarm, R.id.cancel_alarm})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_alarm:
                confirmAlarm();
                break;
            case R.id.cancel_alarm:
                cancelAlarm();
                break;
        }
    }

    private void confirmAlarm() {

    }

    private void cancelAlarm() {

    }
}
