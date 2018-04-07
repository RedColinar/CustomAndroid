package com.example.harry.customandroid.activities.main.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bottombar.GradientTabStrip;
import com.example.harry.customandroid.R;
import com.example.harry.customandroid.activities.main.fragments.WidgetsFragment;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/5.
 */

public class MainAdapter extends FragmentPagerAdapter implements GradientTabStrip.GradientTabAdapter{

    private Context context;

    public MainAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                return WidgetsFragment.newInstance();
            case 1:
                return WidgetsFragment.newInstance();
            case 2:
                return WidgetsFragment.newInstance();
            case 3:
                return WidgetsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Drawable getNormalDrawable(int position, Context context) {
        return null;
    }

    @Override
    public Drawable getSelectedDrawable(int position, Context context) {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
            case 0:
                return context.getString(R.string.main_tab_develop);
            case 1:
                return context.getString(R.string.main_tab_widgets);
            case 2:
                return context.getString(R.string.main_tab_drawables);
            case 3:
                return context.getString(R.string.main_tab_others);
        }
    }

    @Override
    public boolean isTagEnable(int position) {
        return false;
    }

    @Override
    public String getTag(int position) {
        return null;
    }
}
