package deniskaminskiy.paperboy.utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

object ColorUtils {

    private const val FORMAT_HEX = "#%06X"

    fun hexColor(@ColorInt color: Int) = String.format(FORMAT_HEX, (0xFFFFFF and color))

}

fun String.toColor(defaultColor: Int) = try {
    Color.parseColor(this)
} catch (e: Exception) {
    defaultColor
}

fun Context?.compatColor(@ColorRes resId: Int): Int =
    this?.let { ContextCompat.getColor(it, resId) } ?: 0