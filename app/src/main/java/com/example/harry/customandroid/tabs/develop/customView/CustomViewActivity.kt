package com.example.harry.customandroid.tabs.develop.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity
import com.example.harry.customandroid.base.throttleClick
import kotlinx.android.synthetic.main.activity_custom_view.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class CustomViewActivity : BaseActivity() {

    private var isOn = true
    private var isOn2 = true

    override fun getLayoutId(): Int {
        return R.layout.activity_custom_view
    }

    override fun getTitleId(): Int {
        return R.string.custom_view_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_switch.setOnClickListener {
            bt_switch.switch(!isOn)
            isOn = !isOn
        }
        bt_switch2.throttleClick {
            bt_switch2.switch(!isOn2)
            isOn2 = !isOn2
        }

        geometric_transfer_view.sideRotate = -45f
        geometric_transfer_view.sideDegree = 150f

        toolbar_title.setOnClickListener {
            geometric_transfer_view.sideRotate = 0f
            geometric_transfer_view.anotherRotate = 0f

            val animator1 = ObjectAnimator.ofFloat(geometric_transfer_view, "sideRotate", 0f, -45f)
            animator1.startDelay = 500
            animator1.duration = 2000
            animator1.start()

            val animator2 = ObjectAnimator.ofFloat(geometric_transfer_view, "sideDegree", 90f, 450f)
            animator2.startDelay = 500
            animator2.duration = 2000

            val animator3 = ObjectAnimator.ofFloat(geometric_transfer_view, "anotherRotate", 0f, 45f)
            animator3.startDelay = 500
            animator3.duration = 2000

            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(animator1, animator2, animator3)
            animatorSet.start()
        }
    }
}