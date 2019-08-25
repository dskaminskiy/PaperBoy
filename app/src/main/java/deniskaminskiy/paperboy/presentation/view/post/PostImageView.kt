package deniskaminskiy.paperboy.presentation.view.post

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import deniskaminskiy.paperboy.utils.icon.IconRendererFactory
import deniskaminskiy.paperboy.utils.icon.UrlIcon
import deniskaminskiy.paperboy.utils.icon.UrlIconShape

class PostImageView(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    fun show(url: String) {
        show(UrlIcon(url))
    }

    fun show(icon: UrlIcon) {
        IconRendererFactory.create(icon, UrlIconShape.SQUARE)
            .render(this)
    }

}