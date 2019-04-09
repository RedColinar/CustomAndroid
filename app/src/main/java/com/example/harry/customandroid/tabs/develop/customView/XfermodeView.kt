package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.R
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019/4/9
 */
class XfermodeView @JvmOverloads constructor (context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var bitmap: Bitmap
    private val paint = Paint()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val rectF = RectF()
    private val PADDING = dp2px(20f)

    init {
        paint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(PADDING, PADDING, w - PADDING, h - PADDING)
        bitmap = getBitmap(w)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val saved = canvas?.saveLayer(rectF, paint)
        val r = Math.min(width / 2 - PADDING, height / 2 - PADDING)
        canvas?.drawOval(width / 2 - r, height / 2 - r, width / 2 + r, height / 2 + r, paint)
        paint.xfermode = xfermode
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
        paint.xfermode = null
        canvas?.restoreToCount(saved!!)
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