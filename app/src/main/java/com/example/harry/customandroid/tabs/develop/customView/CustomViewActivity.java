package com.example.harry.customandroid.tabs.develop.customView;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

public class CustomViewActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    public int getTitleId() {
        return R.string.custom_view_title;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}