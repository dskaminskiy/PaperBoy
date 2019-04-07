package deniskaminskiy.paperboy.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.R

fun Activity.setStatusBarColor(@ColorInt newColor: Int) {
    with(this.window) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = newColor
    }
}

fun Activity.showKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(view, 0)
}

fun Activity.hideKeyboard() {
    try {
        val view = findViewById<View>(R.id.vgContent)

        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(view.windowToken, 0)
        }
    } catch (e: Exception) {
        //..
    }
}