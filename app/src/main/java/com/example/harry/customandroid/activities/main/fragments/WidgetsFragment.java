package com.example.harry.customandroid.activities.main.fragments;

import android.view.View;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.widgets.clearEditText.ClearEditTextActivity;
import com.example.harry.customandroid.activities.widgets.expand.ExpandActivity;
import com.example.harry.customandroid.activities.widgets.imageEdit.ImageEditActivity;

import butterknife.OnClick;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/5.
 */

public class WidgetsFragment extends BaseFragment {

    public static WidgetsFragment newInstance() {
        return new WidgetsFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_widgets;
    }

    @OnClick({R.id.main_widget_expand, R.id.main_widget_image_edit, R.id.main_widget_clear_edit_text})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_widget_expand:
                start(ExpandActivity.class);
                break;
            case R.id.main_widget_image_edit:
                start(ImageEditActivity.class);
                break;
            case R.id.main_widget_clear_edit_text:
                start(ClearEditTextActivity.class);
                break;
        }
    }
}
