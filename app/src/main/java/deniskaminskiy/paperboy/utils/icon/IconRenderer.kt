package deniskaminskiy.paperboy.utils.icon

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView

interface IconRenderer {

    fun render(imageView: ImageView)

}

object IconRendererFactory {

    private val defaultPlaceholder: Drawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.GRAY)
        }
    }

    /**
     * Возвращает экземпляр интерфейса [IconRenderer], способного отображать все форматы иконок,
     * описанные моделью [Icon]
     *
     * @param icon              - сама иконка, если null - то отобразится [placeholder]
     * @param isCircle          - флаг, указывающий должна ли иконка обрезаться по кругу
     * @param placeholder       - заглушка, используемая при отсутсвии или возникновении проблем с
     *                            установлением иконки
     * @param cornersRadius     - значение закругления углов ( dp )
     */
    @JvmStatic
    fun create(
        icon: Icon?, urlIconShape: UrlIconShape = UrlIconShape.SQUARE,
        placeholder: Drawable = defaultPlaceholder
    ): IconRenderer {

        return when (icon) {
            is UrlIcon -> UrlIconRenderer(icon, urlIconShape, placeholder)
            is DrawableConstantIcon -> DrawableConstantIconRenderer(icon)
            is DrawableIcon -> DrawableIconRenderer(icon)
            else -> PlaceholderIconRenderer(placeholder)
        }
    }

}

data class DrawableConstantIconRenderer(
    private val icon: DrawableConstantIcon
) : IconRenderer {

    override fun render(imageView: ImageView) {
        imageView.setImageResource(icon.container.drawableId)
    }

}

data class DrawableIconRenderer(
    private val icon: DrawableIcon
) : IconRenderer {

    override fun render(imageView: ImageView) {
        imageView.setImageDrawable(icon.drawable)
    }

}

data class PlaceholderIconRenderer(
    private val placeholder: Drawable
) : IconRenderer {
    override fun render(imageView: ImageView) {
        imageView.setImageDrawable(placeholder)
    }
}

data class UrlIconRenderer(
    private val icon: UrlIcon,
    private val shape: UrlIconShape,
    private val placeholder: Drawable
) : IconRenderer {
    override fun render(imageView: ImageView) {
//        GlideApp.with(imageView)
//                .load(icon.url)
//                .placeholder(placeholder)
//                .error(placeholder)
//                .centerCrop()
//                .apply { if (shape == UrlIconShape.CIRCLE) this.circleCrop() }
//                .into(imageView)

        // TODO: CORNERS
        // https://stackoverflow.com/questions/45186181/glide-rounded-corner-transform-issue
    }

}

enum class UrlIconShape(val cornersRadius: Int) {
    SQUARE(0),
    CIRCLE(0),
    SUPER_ELLIPSE(4)
}

