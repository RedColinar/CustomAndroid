package com.example.harry.customandroid.tabs.develop.customView

import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity

class CustomViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_custom_view
    }

    override fun getTitleId(): Int {
        return R.string.custom_view_title
    }
}