package com.example.harry.customandroid.activities.widgets.clearEditText;

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
    public int getLayoutId() {
        return R.layout.activity_clear_edit_text;
    }

    @Override
    public int getTitleId() {
        return R.string.clear_edit_text;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ClearEditTextActivity.class);
        context.startActivity(starter);
    }
}
