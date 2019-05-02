package com.example.harry.customandroid.tabs.develop.scalableImageView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
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
) : View(context, attrs, defStyleAttr), Runnable {

    private lateinit var bitmap: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var originOffsetX = 0f
    private var originOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    private var big = false
    private var scaleFraction = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var smallScale = 1f
    private var bigScale = 2f
    private val scaleObjectAnimator = getAnimator()
    private val scroller = OverScroller(context)

    private fun getAnimator(): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
        objectAnimator.duration = 300
        return objectAnimator
    }

    private val gesture = GestureDetectorCompat(context, object : SimpleOnGestureListener() {
        // 返回 true 非常关键, 只有返回 true 后续事件才会被触发
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            big = !big
            if (big) {
                offsetX = (e!!.x - width / 2f) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2f) * (1 - bigScale / smallScale)
                fixOffset()
                scaleObjectAnimator.start()
            } else {
                scaleObjectAnimator.reverse()
            }
            return false
        }

        // distanceX 不是标准矢量，
        // 标准矢量是末点减初点，而 distanceX 是初点减末点
        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY

                fixOffset()
                invalidate()
            }

            return false
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (big) {
                scroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                        -(((bitmap.width * bigScale - width) / 2).toInt()),
                        ((bitmap.width * bigScale - width) / 2).toInt(),
                        -(((bitmap.height * bigScale - height) / 2).toInt()),
                        ((bitmap.height * bigScale - height) / 2).toInt())

                postOnAnimation(this@ScalableImageView)
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    })

    override fun run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
            postOnAnimation(this)
        }
    }

    private fun fixOffset() {
        offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetX = Math.min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height) / 2)
        offsetY = Math.min(offsetY, (bitmap.height * bigScale - height) / 2)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = getBitmap(w)
        originOffsetY = (h - bitmap.height) / 2f
        originOffsetX = (w - bitmap.width) / 2f

        smallScale = if (bitmap.width / bitmap.height > w / h) {
            // 横向的图片
            (w / bitmap.width).toFloat()
        } else {
            // 纵向的图片
            (h / bitmap.height).toFloat()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gesture.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas?.scale(scale, scale, width / 2f, height / 2f)
        canvas?.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    // 输出图片的宽高 = 原图片的宽高 / inSampleSize * (inTargetDensity / inDensity)
    private fun getBitmap(border: Int): Bitmap {
        val res = R.drawable.xiaomai
        // val res = R.drawable.long_jpg
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, res, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = border
        return BitmapFactory.decodeResource(resources, res, options)
    }
}