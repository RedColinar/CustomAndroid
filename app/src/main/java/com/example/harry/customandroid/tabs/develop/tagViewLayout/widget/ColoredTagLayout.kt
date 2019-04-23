package com.example.harry.customandroid.tabs.develop.tagViewLayout.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import java.util.*

/**
 * Created by panqiang at 2019-04-22
 */
class ColoredTagLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val childRect = LinkedList<Rect>()

    init {
        for (i in 0 until childCount) {
            val rect = Rect()
            childRect.add(rect)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                widthUsed,
                heightMeasureSpec,
                heightUsed
            )

            val rect = childRect[i]

            rect.set(
                widthUsed,
                heightUsed,
                widthUsed + child.measuredWidth,
                Math.max(heightUsed, child.measuredHeight)
            )
            widthUsed += child.measuredWidth

        }

        setMeasuredDimension(widthUsed, heightUsed)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).layout(
                childRect[i].left,
                childRect[i].top,
                childRect[i].right,
                childRect[i].bottom
            )
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}