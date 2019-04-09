package com.example.harry.customandroid.tabs.develop.customView

import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_custom_view.*

class CustomViewActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_custom_view
    }

    override fun getTitleId(): Int {
        return R.string.custom_view_title
    }

    override fun onResume() {
        super.onResume()

//        var index = 0
//        val timer = Timer()
//        val timerTask = object : TimerTask() {
//            override fun run() {
//                dash_view.pointTo(index)
//                index++
//                index %= DashView.STEP + 1
//            }
//        }
//
//        timer.schedule(timerTask, 1000, 1000)
        dash_view.pointTo(0)
    }
}