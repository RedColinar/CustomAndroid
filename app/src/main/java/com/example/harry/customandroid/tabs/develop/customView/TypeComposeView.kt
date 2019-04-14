package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.R
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019/4/14
 * 图文混排
 */
class TypeComposeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val text = "Clutching their purchases, Mr. Weasley in the lead, they all hurried into the wood, following the lantern-lit trail. They could hear the sounds of thousands of people moving around them, shouts and laughter, snatches of singing. The atmosphere of feverish excitement was highly infectious; Harry couldn't stop grinning. They walked through the wood for twenty minutes, talking and joking loudly, until at last they emerged on the other side and found themselves in the shadow of a gigantic stadium. Though Harry could see only a fraction of the immense gold walls surrounding the field, he could tell that ten cathedrals would fit comfortably inside it."

    private val textPaint = TextPaint(ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap
    val floatArray = FloatArray(1)
    var bitmapLeft = 0f
    var bitmapTop = 0f
    var bitmapRight = 0f
    var bitmapBottom = 0f

    init {
        textPaint.color = Color.BLACK
        textPaint.textSize = dp2px(16f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(dp2px(100f).toInt())
        bitmapLeft = (width - bitmap.width).toFloat()
        bitmapTop = dp2px(50f)
        bitmapRight = width.toFloat()
        bitmapBottom = bitmapTop + bitmap.height
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap, bitmapLeft, bitmapTop, textPaint)

        var start = 0
        var count = 0
        var yOffset = textPaint.fontSpacing

        while (start + count < text.length) {
            count = if (yOffset > bitmapTop && (yOffset - textPaint.fontSpacing) < bitmapBottom) {
                textPaint.breakText(text, start, text.length, true, bitmapLeft, floatArray)
            } else {
                textPaint.breakText(text, start, text.length, true, width.toFloat(), floatArray)
            }
            canvas?.drawText(text, start, start + count, 0f, yOffset, textPaint)
            start += count
            yOffset += textPaint.fontSpacing
        }
    }

    private fun getBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.geralt, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.geralt, options)
    }
}