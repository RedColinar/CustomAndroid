package com.example.harry.customandroid.tabs.develop.customView

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.harry.customandroid.R

/**
 * Created by panqiang at 2019/4/14
 * 几何变换
 */
class GeometricXferView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var bitmap: Bitmap
    private var bitmapWidth = 0f
    private var cx = 0
    private var cy = 0

    private val camera = Camera()
    // canvas旋转
    var sideDegree = 90f
        set(sideDegree) {
            field = sideDegree
            invalidate()
        }
    // 相机一半旋转
    var sideRotate = 0f
        set(value) {
            field = value
            invalidate()
        }
    // 相机另一半旋转
    var anotherRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        camera.setLocation(0f, 0f, -6 * Resources.getSystem().displayMetrics.density)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(h * 2 / 3)
        bitmapWidth = bitmap.width.toFloat()

        cx = w / 2
        cy = h / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // side
        canvas?.save()
        camera.save()
        camera.rotateX(sideRotate)
        canvas?.translate(cx.toFloat(), cy.toFloat())
        canvas?.rotate(-sideDegree)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(-bitmapWidth, -bitmapWidth, bitmapWidth, 0f)
        canvas?.rotate(sideDegree)
        canvas?.translate(-cx.toFloat(), -cy.toFloat())
        canvas?.drawBitmap(bitmap, cx - bitmapWidth / 2f, cy - bitmapWidth / 2f, paint)
        camera.restore()
        canvas?.restore()

        //another
        canvas?.save()
        camera.save()
        camera.rotateX(anotherRotate)
        canvas?.translate(cx.toFloat(), cy.toFloat())
        canvas?.rotate(-sideDegree)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(-bitmapWidth, 0f, bitmapWidth, bitmapWidth)
        canvas?.rotate(sideDegree)
        canvas?.translate(-cx.toFloat(), -cy.toFloat())
        canvas?.drawBitmap(bitmap, cx - bitmapWidth / 2f, cy - bitmapWidth / 2f, paint)
        camera.restore()
        canvas?.restore()
    }

    private fun getBitmap(width: Int): Bitmap {
        val res = R.drawable.xiaomai
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, res, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, res, options)
    }
}