package com.example.harry.customandroid.tabs.develop.customView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019-04-24
 */
class SwitchButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val width = dp2px(45)
    private val height = dp2px(20)
    private val radius = dp2px(9)
    private val startCenter = dp2px(36)
    private val endCenter = dp2px(9)

    private val paint = Paint(ANTI_ALIAS_FLAG)

    private var isOn = true
    private val onColor = Color.parseColor("#0F8CFF")
    private val offColor = Color.parseColor("#FFFFFF")
    private val borderColor = Color.parseColor("#EEEEEE")
    private var switchFraction = 1f
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height.toInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = borderColor
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = dp2px(5f)
        canvas?.drawLine(0f, height / 2f, width, height / 2f, paint)

        paint.color = onColor
        paint.strokeWidth = 1f
        canvas?.drawCircle(endCenter + (startCenter - endCenter) * switchFraction, height / 2, radius, paint)

        paint.color = offColor
        canvas?.drawCircle(endCenter + (startCenter - endCenter) * switchFraction, height / 2, radius * (1 - switchFraction), paint)
    }

    @Synchronized fun switch(toOn: Boolean) {
        val objectAnimator = getSwitchAnimator()
        if (toOn) {
            objectAnimator.reverse()
        } else {
            objectAnimator.start()
        }
        isOn = toOn
    }

    private fun getSwitchAnimator(): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(this@SwitchButton, "switchFraction", 1f, 0f)
        objectAnimator.duration = 300
        return objectAnimator
    }
}