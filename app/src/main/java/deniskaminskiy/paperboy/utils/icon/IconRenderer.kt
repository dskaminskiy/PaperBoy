package deniskaminskiy.paperboy.utils.icon

import android.widget.ImageView
import androidx.annotation.DrawableRes
import deniskaminskiy.paperboy.R

interface IconRenderer {

    fun render(imageView: ImageView)

}

object IconRendererFactory {

    /**
     * Возвращает экземпляр интерфейса [IconRenderer], способного отображать все форматы иконок,
     * описанные моделью [Icon]
     *
     * @param icon              - сама иконка, если null - то отобразится [placeholder]
     * @param isCircle          - флаг, указывающий должна ли иконка обрезаться по кругу
     * @param placeholder       - заглушка, используемая при отсутсвии или возникновении проблем с
     *                            установлением иконки
     */
    @JvmStatic
    fun create(icon: Icon?, isCircle: Boolean = false,
               placeholder: Int = R.drawable.oval_solid_print_40): IconRenderer {

        return when (icon) {
            is UrlIcon              -> UrlIconRenderer(icon, isCircle, placeholder)
            is DrawableConstantIcon -> DrawableConstantIconRenderer(icon)
            is DrawableIcon         -> DrawableIconRenderer(icon)
            else                    -> PlaceholderIconRenderer(placeholder)
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
        @DrawableRes private val placeholder: Int
) : IconRenderer {
    override fun render(imageView: ImageView) {
        imageView.setImageResource(placeholder)
    }
}

data class UrlIconRenderer(
        private val icon: UrlIcon,
        private val isCircle: Boolean,
        @DrawableRes private val placeholder: Int
) : IconRenderer {
    override fun render(imageView: ImageView) {
//        GlideApp.with(imageView)
//                .load(icon.url)
//                .placeholder(placeholder)
//                .error(placeholder)
//                .centerCrop()
//                .apply { if (isCircle) this.circleCrop() }
//                .into(imageView)
    }

}

