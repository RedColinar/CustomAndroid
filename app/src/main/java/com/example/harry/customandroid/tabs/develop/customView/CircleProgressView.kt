package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.R
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019/4/14
 */
class CircleProgressView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    val strokePaint = Paint(ANTI_ALIAS_FLAG)
    val textPaint = TextPaint(ANTI_ALIAS_FLAG)

    var cx: Float = 0f
    var cy: Float = 0f
    val r: Float = dp2px(100f)
    val text = "AaJjGg"
    lateinit var rect: RectF

    init {
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = dp2px(10f)

        textPaint.style = Paint.Style.FILL
        textPaint.color = resources.getColor(R.color.colorAccent, null)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = dp2px(50f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = w / 2f
        cy = h / 2f
        rect = RectF(cx - r, cy - r, cx + r, cy + r)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        strokePaint.color = resources.getColor(R.color.colorRipple, null)
        canvas?.drawOval(rect, strokePaint)

        strokePaint.color = resources.getColor(R.color.colorAccent, null)
        strokePaint.strokeCap = Paint.Cap.ROUND
        canvas?.drawArc(rect, -90f, 240f, false, strokePaint)

        canvas?.drawText(text, 0, text.length, cx, cy - (textPaint.ascent() + textPaint.descent()) / 2, textPaint)
    }
}