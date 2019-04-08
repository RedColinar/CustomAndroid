package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019/4/8
 */
class PieView @JvmOverloads constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    private val colors = arrayOf(
            Color.parseColor("#FF8247"),
            Color.parseColor("#912CEE"),
            Color.parseColor("#FFFF00"),
            Color.parseColor("#ADFF2F"))
    private val angles = arrayOf(90f, 70f, 140f, 60f)

    private val paint = Paint()
    private var currentAngle = 0f
    private var rect = RectF()

    private val RADIUS = dp2px(150f)

    private var selectIndex = 0
    private val translate = dp2px(20f)

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = colors[0]
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rect = RectF(w / 2f - RADIUS, h / 2f - RADIUS, w / 2f + RADIUS, h / 2f + RADIUS)

        selectAt(1)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        currentAngle = 0f
        for (i in angles.indices) {
            if (i == selectIndex) {
                canvas?.save()

                canvas?.translate(
                        translate * Math.cos(Math.toRadians((currentAngle + angles[i] / 2).toDouble())).toFloat(),
                        translate * Math.sin(Math.toRadians((currentAngle + angles[i] / 2).toDouble())).toFloat())
            }

            paint.color = colors[i]
            canvas?.drawArc(rect, currentAngle, angles[i], true, paint)
            currentAngle += angles[i]

            if (i == selectIndex) {
                canvas?.restore()
            }
        }
    }

    fun selectAt(index: Int) {
        selectIndex = index
        invalidate()
    }

}
