package com.example.harry.customandroid.tabs.develop.coorinatorLayout

import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_coordinator_1.*

/**
 * Created by panqiang at 2019-04-22
 */
class CoordinatorLayoutActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_coordinator_1
    }

    override fun getTitleId(): Int {
        return R.string.coordinator_layout_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}