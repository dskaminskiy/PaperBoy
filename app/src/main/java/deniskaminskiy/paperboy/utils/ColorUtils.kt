package deniskaminskiy.paperboy.utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun String.toColor(defaultColor: Int) = try {
    Color.parseColor(this)
} catch (e: Exception) {
    defaultColor
}

fun Context?.compatColor(@ColorRes resId: Int): Int =
    this?.let { ContextCompat.getColor(it, resId) } ?: 0