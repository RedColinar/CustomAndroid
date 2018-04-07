package com.example.harry.customandroid.activities.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bottombar.GradientTabStrip;
import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.main.adapters.MainAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vpFragments = findViewById(R.id.main_vp_fragments);
        GradientTabStrip gtsTabs = findViewById(R.id.main_gts_tabs);

        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), this);
        vpFragments.setAdapter(adapter);
        gtsTabs.setAdapter(adapter);

        gtsTabs.bindViewPager(vpFragments);
    }
}
