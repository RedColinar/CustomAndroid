package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.utils.dp2px

class CanvasView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(ANTI_ALIAS_FLAG)
    private val path = Path()

    private val text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
    private lateinit var staticLayout: StaticLayout

    init {
        paint.textSize = dp2px(20f)
        paint.style = Paint.Style.STROKE

        textPaint.textSize = dp2px(30f)
    }

    companion object {
        const val text = "Hello PQ"
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.moveTo(0f, 0f)
        path.lineTo(w / 2f, h.toFloat())
        path.lineTo(w.toFloat(), 0f)

        staticLayout = StaticLayout(text1, textPaint, w, Layout.Alignment.ALIGN_NORMAL,
                1f, 0f, true)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawPath(path, paint)

        canvas?.drawTextOnPath(text, path, 100f, 0f, textPaint)

        canvas?.drawText(text, 200f, 200f, paint)

        staticLayout.draw(canvas)

    }


}
