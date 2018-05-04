package com.example.harry.customandroid.activities.develop.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.BaseActivity;

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

    public static void start(Context context) {
        Intent starter = new Intent(context, AlarmActivity.class);
        context.startActivity(starter);
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
