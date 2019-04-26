package com.example.harry.customandroid.tabs.develop.customView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019-04-25
 */
class SwitchButton2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var isOn = true
    private var fraction = 1f
        set(value) {
            field = value
            requestLayout()
            switchOval.invalidate()
        }

    private val padding = dp2px(2.5f)
    private val mWidth = dp2px(50)
    private val mHeight = dp2px(25)
    private val radius = dp2px(9)
    private val startCenter = dp2px(36)
    private val endCenter = dp2px(9)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val onColor = Color.parseColor("#0F8CFF")
    private val offColor = Color.parseColor("#FFFFFF")
    private val borderColor = Color.parseColor("#EEEEEE")

    private val switchOval = SwitchOval(context)
    private val objectAnimator = getSwitchAnimator()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        removeAllViews()
        addView(switchOval)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        switchOval.measure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(mWidth.toInt(), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(mHeight.toInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        switchOval.layout(
            (padding + fraction * (startCenter - endCenter)).toInt(),
            (height / 2 - radius).toInt(),
            (padding + fraction * (startCenter - endCenter) + 2 * radius).toInt(),
            (height / 2 + radius).toInt()
        )
    }

    override fun dispatchDraw(canvas: Canvas?) {
        paint.color = borderColor
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dp2px(5f)
        canvas?.drawLine(2 * padding, height / 2f, width - 2 * padding, height / 2f, paint)

        super.dispatchDraw(canvas)
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    fun switch(toOn: Boolean) {
        if (toOn) {
            objectAnimator.reverse()
        } else {
            objectAnimator.start()
        }
        isOn = toOn
    }

    private fun getSwitchAnimator(): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(this, "fraction", 1f, 0f)
        objectAnimator.duration = 250
        return objectAnimator
    }

    private inner class SwitchOval @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

        init {
            elevation = dp2px(5)
            val ovalShape = OvalShape()
            ovalShape.resize(2 * radius, 2 * radius)
            background = ShapeDrawable(ovalShape)
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(2 * radius.toInt(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(2 * radius.toInt(), MeasureSpec.EXACTLY)
            )
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)
            paint.color = onColor
            paint.style = Paint.Style.FILL
            canvas?.drawCircle(width / 2f, height / 2f, radius, paint)

            paint.color = offColor
            canvas?.drawCircle(width / 2f, height / 2f, radius * (1 - fraction), paint)
        }
    }
}
