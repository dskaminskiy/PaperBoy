package deniskaminskiy.paperboy.utils.icon

import androidx.annotation.DrawableRes
import deniskaminskiy.paperboy.R


sealed class Icon

data class UrlIcon(val url: String) : Icon()

sealed class ConstantIcon : Icon()

data class DrawableConstantIcon(
        val container: IconDrawableContainer
) : ConstantIcon()


enum class IconDrawableContainer(
    @DrawableRes val drawableId: Int
) {
    ADD(R.drawable.ic_add),
    ARROW_BACK(R.drawable.ic_arrow_back),
    TRASH(R.drawable.ic_trash)
}

