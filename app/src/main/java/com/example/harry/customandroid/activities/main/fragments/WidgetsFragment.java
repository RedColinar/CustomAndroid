package com.example.harry.customandroid.activities.main.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.widgets.ClearEditText.ClearEditTextActivity;
import com.example.harry.customandroid.activities.widgets.Expand.ExpandActivity;
import com.example.harry.customandroid.activities.widgets.ImageEdit.ImageEditActivity;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/5.
 */

public class WidgetsFragment extends BaseFragment implements View.OnClickListener{

    public static WidgetsFragment newInstance() {
        return new WidgetsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_widgets, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClick(view, R.id.main_widget_expand);
        setClick(view, R.id.main_widget_image_edit);
        setClick(view, R.id.main_widget_clear_edit_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_widget_expand:
                ExpandActivity.start(context);
                break;
            case R.id.main_widget_image_edit:
                ImageEditActivity.start(context);
                break;
            case R.id.main_widget_clear_edit_text:
                ClearEditTextActivity.start(context);
                break;
        }
    }

    private void setClick(View view, @IdRes int id) {
        view.findViewById(id).setOnClickListener(this);
    }
}
