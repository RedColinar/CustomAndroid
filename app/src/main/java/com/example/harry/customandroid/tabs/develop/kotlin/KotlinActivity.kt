package com.example.harry.customandroid.tabs.develop.kotlin

import com.example.harry.customandroid.R
import com.example.harry.customandroid.base.BaseActivity

import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin
    }

    override fun getTitleId(): Int {
        return R.string.kotlin_title
    }

    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            // `obj` 在该条件分支内自动转换成 `String`
            return obj.length
        }

        // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
        return null
    }
}
