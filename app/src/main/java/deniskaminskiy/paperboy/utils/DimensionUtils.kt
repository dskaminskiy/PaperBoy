package deniskaminskiy.paperboy.utils

import android.content.Context
import android.util.DisplayMetrics

fun Int.dp(context: Context?): Int =
    context?.let {
        val dpCoefficient = it.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

        (this * dpCoefficient).toInt()
    } ?: 0

fun Int.px(context: Context?): Float = context?.let {
    this * (it.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
} ?: 0f

