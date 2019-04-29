package com.example.harry.customandroid.tabs.develop.scalableImageView

import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity

class ScalableActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.layout_scalable_iamge
    }

    override fun getTitleId(): Int {
        return R.string.scalable_image_title
    }
}