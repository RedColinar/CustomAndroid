package com.example.harry.customandroid.activities.main.fragments;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/5.
 */

public class BaseFragment extends Fragment {

    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
