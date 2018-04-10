package com.example.harry.customandroid.activities.widgets.ClearEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.BaseActivity;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/10.
 */

public class ClearEditTextActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_edit_text);
        initToolBar();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> this.finish());
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ClearEditTextActivity.class);
        context.startActivity(starter);
    }
}
