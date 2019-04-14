package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.R
import com.example.harry.customandroid.utils.Utils

/**
 * Created by panqiang at 2019/4/14
 * 几何变换
 */
class GeometricXferView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap
    private lateinit var rectF: RectF
    private var bitmapWidth = 0f
    private var cx = 0
    private var cy = 0

    var degree = 45f
    private val camera = Camera()

    init {
        camera.rotateX(degree)
        camera.setLocation(0f, 0f, -6 * Resources.getSystem().displayMetrics.density)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(h / 2)
        bitmapWidth = bitmap.width.toFloat()
        rectF = RectF(0f, 0f , w.toFloat(), h.toFloat())

        cx = w / 2
        cy = h / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.translate(bitmapWidth / 2, bitmapWidth / 2)
        canvas?.rotate(-30f)
        canvas?.clipRect(- bitmapWidth, - bitmapWidth, bitmapWidth, 0f)
        canvas?.rotate(30f)
        canvas?.translate(- bitmapWidth / 2, - bitmapWidth / 2)
        canvas?.drawBitmap(bitmap, cx - bitmap.width / 2f, cy - bitmap.height / 2f, paint)
        canvas?.restore()

        canvas?.save()
        canvas?.translate(cx.toFloat(), cy.toFloat())
        camera.applyToCanvas(canvas)
        canvas?.rotate(-30f)
        canvas?.clipRect(bitmap.width / -2f, 0f, bitmap.width / 2f, bitmap.width / 2f)
        canvas?.rotate(30f)
        canvas?.translate(-cx.toFloat(), -cy.toFloat())
        canvas?.drawBitmap(bitmap, cx - bitmap.width / 2f, cy - bitmap.height / 2f, paint)
        canvas?.restore()
    }

    private fun getBitmap(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher, options)
    }
}