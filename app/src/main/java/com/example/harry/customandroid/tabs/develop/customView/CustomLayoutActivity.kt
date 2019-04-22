package com.example.harry.customandroid.tabs.develop.customView

import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity

/**
 * Created by panqiang at 2019-04-22
 */
class CustomLayoutActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_custom_layout
    }

    override fun getTitleId(): Int {
        return R.string.custom_layout_title
    }

}