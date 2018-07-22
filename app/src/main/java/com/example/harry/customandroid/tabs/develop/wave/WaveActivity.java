package com.example.harry.customandroid.tabs.develop.wave;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by PanQiang on 2018/7/9
 */
public class WaveActivity extends BaseActivity {

    @BindView(R.id.wave)
    WaveView waveView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wave;
    }

    @Override
    public int getTitleId() {
        return R.string.wave_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
