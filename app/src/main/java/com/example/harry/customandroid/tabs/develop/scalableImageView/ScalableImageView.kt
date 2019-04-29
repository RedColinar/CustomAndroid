package com.example.harry.customandroid.tabs.develop.scalableImageView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.R

/**
 * 1. 图片有大小两种尺寸，小图的长边顶着 View 的边，大图的长短边都超出 View 的边界；
 * 2. 双击切换两种尺寸，并且有动画过程；
 * 3. 大尺寸时可以用手指进行滑动；
 * 4. 滑动松手时会惯性滑动；
 * 5. 滑动与惯性滑动时都不会超出范围。
 */
class ScalableImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var bitmap: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var offsetX = 0f
    private var offsetY = 0f

    private val smallScale = 1f
    private val bigScale = 2f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(w)
        offsetY = (h - bitmap.height) / 2f
        offsetX = (w - bitmap.width) / 2f

        if (bitmap.width / bitmap.height > w / h) {
        } else {
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.scale(bigScale, bigScale, width / 2f, height / 2f)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    // 输出图片的宽高 = 原图片的宽高 / inSampleSize * (inTargetDensity / inDensity)
    private fun getBitmap(border: Int): Bitmap {
        val res = R.drawable.xiaomai
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, res, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = border
        return BitmapFactory.decodeResource(resources, res, options)
    }
}