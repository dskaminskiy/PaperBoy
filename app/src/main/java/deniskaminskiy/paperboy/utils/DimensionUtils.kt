package deniskaminskiy.paperboy.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

fun Int.dp(context: Context?): Int =
    context?.let {
        val dpCoefficient = context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT

        (this * dpCoefficient).toInt()
    } ?: 0

