package com.example.harry.customandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.harry.customandroid.R;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/5.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();

    public abstract @LayoutRes int getLayoutId();
    public abstract @StringRes int getTitleId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Log.d(TAG, "onCreate: ");
        initToolbar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null) return;

        TextView title = mToolbar.findViewById(R.id.toolbar_title);
        title.setText(getTitleId());

        if (hasBackIcon()) {
            mToolbar.setNavigationIcon(R.drawable.ic_action_back);
            mToolbar.setNavigationOnClickListener(v -> this.finish());
        }
    }

    protected void start(Class<?> cls) {
        Intent starter = new Intent(this, cls);
        startActivity(starter);
    }

    protected boolean hasBackIcon() {
        return true;
    }
}
