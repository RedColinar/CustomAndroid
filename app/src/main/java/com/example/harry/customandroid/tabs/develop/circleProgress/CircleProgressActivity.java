package com.example.harry.customandroid.tabs.develop.circleProgress;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;

import butterknife.BindView;

/**
 * @author panqiang
 * @version 1.0
 * @date 2018/8/28 10:49
 * @description
 */
public class CircleProgressActivity extends BaseActivity {
    @BindView(R.id.circle_progress)
    CircleProgressView circleProgressView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_progress;
    }

    @Override
    public int getTitleId() {
        return R.string.circle_progress;
    }

    @Override
    protected void onResume() {
        super.onResume();
        circleProgressView.startAnim(0.8f, true);
    }
}
