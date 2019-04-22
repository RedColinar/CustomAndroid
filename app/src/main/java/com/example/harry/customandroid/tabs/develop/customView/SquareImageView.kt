package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by panqiang at 2019-04-22
 */
class SquareImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}