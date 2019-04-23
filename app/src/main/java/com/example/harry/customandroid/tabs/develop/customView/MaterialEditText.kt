package com.example.harry.customandroid.tabs.develop.customView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.TextPaint
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.harry.customandroid.R
import com.example.harry.customandroid.utils.dp2px

/**
 * Created by panqiang at 2019-04-19
 */
class MaterialEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val textPaint = TextPaint()
    private var floatLabelShowing = false
    private var progress = 0f
        set(value) {
            field = value
            invalidate()
        }
    private lateinit var objectAnimator: ObjectAnimator

    companion object {
        private val TEXT_SIZE = dp2px(12f)
        private val TEXT_MARGIN = dp2px(8f)
        private val HINT_PADDING_LEFT = dp2px(5f)
    }

    init {
        textPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        textPaint.textSize = TEXT_SIZE

        setPadding(
            paddingLeft,
            (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(),
            paddingRight,
            paddingBottom
        )

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s) && floatLabelShowing) {
                    objectAnimator = getAnimator()
                    objectAnimator.reverse()
                    floatLabelShowing = false
                } else if (!TextUtils.isEmpty(s) && !floatLabelShowing) {
                    objectAnimator = getAnimator()
                    objectAnimator.start()
                    floatLabelShowing = true
                }
            }
        })
    }

    private fun getAnimator(): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(this@MaterialEditText, "progress", 0f, 1f)
        objectAnimator.duration = 500
        return objectAnimator
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!TextUtils.isEmpty(hint)) {

            textPaint.alpha = (progress * 0xff).toInt()
            textPaint.textSize = textSize - (textSize - TEXT_SIZE) * progress
            canvas?.drawText(
                hint.toString(),
                HINT_PADDING_LEFT,
                textPaint.fontSpacing + TEXT_MARGIN + dp2px(2f) + dp2px(12f) * (1 - progress),
                textPaint
            )
        }
    }
}