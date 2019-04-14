package com.example.harry.customandroid.tabs.develop.customView

import android.os.Bundle
import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_custom_view.*
import java.util.*

class CustomViewActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_custom_view
    }

    override fun getTitleId(): Int {
        return R.string.custom_view_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}