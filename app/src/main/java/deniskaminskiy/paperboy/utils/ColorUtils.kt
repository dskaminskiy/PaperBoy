package deniskaminskiy.paperboy.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

object ColorUtils {

    private const val FORMAT_HEX = "#%06X"

    fun hexColor(@ColorInt color: Int): String = String.format(FORMAT_HEX, (0xFFFFFF and color))

    /**
     *  Возвращает drawable с наложенным tint заданного цвета.
     *  @param color - int resource color
     *  @param drawable - drawable from resources.
     */
    fun paintDrawable(context: Context, @ColorInt color: Int, @DrawableRes drawableId: Int): Drawable =
        ContextCompat.getDrawable(context, drawableId)
            ?.let(DrawableCompat::wrap)
            ?.let(Drawable::mutate)
            ?.also {
                DrawableCompat.setTint(it, color)
            }
            ?: ColorDrawable(Color.TRANSPARENT)

}

fun String.toColor(defaultColor: Int) = try {
    Color.parseColor(this)
} catch (e: Exception) {
    defaultColor
}

fun Context?.compatColor(@ColorRes resId: Int): Int =
    this?.let { ContextCompat.getColor(it, resId) } ?: 0