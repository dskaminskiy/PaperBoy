package deniskaminskiy.paperboy.utils.icon

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.ColorUtils


sealed class Icon

data class UrlIcon(val url: String) : Icon()

data class DrawableIcon(val drawable: Drawable) : Icon()

sealed class ConstantIcon : Icon(), PaintedIcon

data class DrawableConstantIcon(
    val container: DrawableIconConstant
) : ConstantIcon() {

    override fun toColor(context: Context, color: Int): DrawableIcon =
        DrawableIcon(ColorUtils.paintDrawable(context, color, container.drawableId))

}

enum class DrawableIconConstant(
    @DrawableRes val drawableId: Int
) {
    ADD(R.drawable.ic_add),
    ARROW_BACK(R.drawable.ic_arrow_back),
    TRASH(R.drawable.ic_trash),
    ARROW_FORWARD(R.drawable.ic_arrow_forward),
    WARNING(R.drawable.ic_warning),
    UNREAD(R.drawable.ic_unread),
    BOOKMARK(R.drawable.ic_bookmark),
    SETTINGS(R.drawable.ic_settings)
}

interface PaintedIcon {

    fun toColor(context: Context, @ColorInt color: Int): DrawableIcon

}

