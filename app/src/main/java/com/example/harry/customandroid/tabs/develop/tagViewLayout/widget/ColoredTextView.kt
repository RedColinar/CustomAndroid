package com.example.harry.customandroid.tabs.develop.tagViewLayout.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Color.parseColor
import android.graphics.Paint
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import com.example.harry.customandroid.utils.dp2px
import java.util.*


/**
 * Created by panqiang at 2019-04-22
 */
class ColoredTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var paint = Paint(ANTI_ALIAS_FLAG)

    companion object {
        private val COLORS = intArrayOf(
            parseColor("#E91E63"),
            parseColor("#673AB7"),
            parseColor("#3F51B5"),
            parseColor("#2196F3"),
            parseColor("#009688"),
            parseColor("#FF9800"),
            parseColor("#FF5722"),
            parseColor("#795548")
        )
        private val TEXT_SIZES = intArrayOf(16, 22, 28)
        private val random = Random()
        private val CORNER_RADIUS = dp2px(4)
        private val X_PADDING = dp2px(16).toInt()
        private val Y_PADDING = dp2px(8).toInt()
    }

    init {
        setTextColor(Color.WHITE)
        textSize = TEXT_SIZES[random.nextInt(3)].toFloat()
        paint.color = COLORS[random.nextInt(COLORS.size)]
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRoundRect(
            0f, 0f,
            width.toFloat(),
            height.toFloat(),
            CORNER_RADIUS,
            CORNER_RADIUS,
            paint
        )
        super.onDraw(canvas)
    }
}