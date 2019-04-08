package com.example.harry.customandroid.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by panqiang at 2019/4/8
 */

fun dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
}