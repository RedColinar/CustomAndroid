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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var lineHeight = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                heightUsed
            )

            if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
                if (widthUsed + child.measuredWidth >= MeasureSpec.getSize(widthMeasureSpec)) {
                    heightUsed += Math.max(lineHeight, child.measuredHeight)
                    lineHeight = 0
                    widthUsed = 0

                    measureChildWithMargins(
                        child,
                        widthMeasureSpec,
                        0,
                        heightMeasureSpec,
                        heightUsed
                    )
                }
            }

            val rect = if (childRect.size <= childCount) {
                val rect = Rect()
                childRect.add(rect)
                rect
            } else {
                childRect[i]
            }

            lineHeight = Math.max(lineHeight, child.measuredHeight)

            rect.set(
                widthUsed,
                heightUsed,
                widthUsed + child.measuredWidth,
                heightUsed + lineHeight
            )

            widthUsed += child.measuredWidth
        }

        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), heightUsed + lineHeight)
        }
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