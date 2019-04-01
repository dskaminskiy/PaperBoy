package deniskaminskiy.paperboy.utils

import android.app.Activity
import android.view.WindowManager
import androidx.annotation.ColorInt

fun Activity.setStatusBarColor(@ColorInt newColor: Int) {
    with(this.window) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = newColor
    }
}