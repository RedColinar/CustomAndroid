package com.example.harry.customandroid.base

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.*

/**
 * Created by panqiang at 2019-04-23
 */

/**
 * 点击节流
 */
fun <T : View> T.throttleClick(click: (T) -> Unit) = setOnClickListener {
    isEnabled = false
    click(this)
    isEnabled = true
}

/**
 * 输入去抖
 */
private var textChangeJob: Job? = null

fun <T : TextView> T.debounceInput(textChangedCallback: (text: String) -> Unit) {
    debounceInput(500L, textChangedCallback)
}

fun <T : TextView> T.debounceInput(delay: Long, textChangedCallback: (text: String) -> Unit) =
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val searchText = text.toString().trim()
            textChangeJob?.cancel()
            textChangeJob = GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT) {
                delay(delay)
                textChangedCallback(searchText)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })

fun dp2px(dp: Int): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return dp * scale + 0.5f
}
