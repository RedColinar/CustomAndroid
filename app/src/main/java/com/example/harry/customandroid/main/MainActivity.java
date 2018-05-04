package com.example.harry.customandroid.main;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.bottombar.GradientTabStrip;
import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;
import com.example.harry.customandroid.main.adapters.MainAdapter;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager vpFragments = findViewById(R.id.main_vp_fragments);
        GradientTabStrip gtsTabs = findViewById(R.id.main_gts_tabs);

        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), this);
        vpFragments.setAdapter(adapter);
        gtsTabs.setAdapter(adapter);

        gtsTabs.bindViewPager(vpFragments);
    }

    @Override
    public int getTitleId() {
        return R.string.app_name;
    }

    @Override
    protected boolean hasBackIcon() {
        return false;
    }
}
