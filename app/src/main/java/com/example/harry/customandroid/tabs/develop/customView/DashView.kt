package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019/4/8
 */
class DashView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    private var path = Path()
    private lateinit var effect: PathDashPathEffect
    private var pointerPath = Path()
    private var paint = Paint()

    companion object {
        const val ANGLE: Float = 120f
        const val STEP = 10
        const val START_ANGLE = 90 + ANGLE / 2
        const val ANGLE_PER_STEP = (360 - ANGLE) / STEP

        val PADDING: Float = dp2px(10f)
        val STROKE_WIDTH = dp2px(1f)
        val STROKE_LENGTH = dp2px(10f)
        val POINTER_LENGTH = dp2px(210f)
    }

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.strokeWidth = STROKE_WIDTH
        paint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val r = Math.min(w / 2 - dp2px(PADDING), h / 2 - dp2px(PADDING))
        val rect = RectF(w / 2f - r, h / 2f - r, w / 2f + r, h / 2f + r)
        path.addArc(rect, START_ANGLE, 360 - ANGLE)

        val pathMeasure = PathMeasure(path, false)
        val advance = (pathMeasure.length - STROKE_WIDTH) / STEP

        val effectPath = Path()
        effectPath.addRect(0f, 0f, STROKE_WIDTH, STROKE_LENGTH, Path.Direction.CW)
        effect = PathDashPathEffect(effectPath, advance, 0f, PathDashPathEffect.Style.MORPH)

        pointTo(0)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)

        paint.pathEffect = effect
        canvas?.drawPath(path, paint)
        paint.pathEffect = null

        canvas?.drawPath(pointerPath, paint)
    }

    fun pointTo(index: Int) {
        val cx: Float = width / 2f
        val cy: Float = height / 2f

        val radius = Math.toRadians((START_ANGLE + index * ANGLE_PER_STEP).toDouble())
        val cosA: Double = Math.cos(radius)
        val sinA: Double = Math.sin(radius)

        pointerPath.reset()
        pointerPath.moveTo(cx, cy)
        pointerPath.lineTo(
                cx + POINTER_LENGTH * cosA.toFloat(),
                cx + POINTER_LENGTH * sinA.toFloat())

        invalidate()
    }
}