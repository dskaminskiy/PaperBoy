package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.data.ItemConstantIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemDefaultIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemUrlIconPresModel
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.icon.*
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesProvider
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.view.clearSrc
import deniskaminskiy.paperboy.utils.view.goneIf
import deniskaminskiy.paperboy.utils.view.isVisible

class FlipImageView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object {
        const val ICON_CORNER_RADIUS = 6
    }

    private val iconCornerRadiusDp = ICON_CORNER_RADIUS.dp(context).toFloat()

    private val resources: ResourcesProvider by lazy { AndroidResourcesProvider.create(context) }

    private val iconPlaceholder: Drawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(resources.provideColor(R.color.print15))
            cornerRadius = iconCornerRadiusDp
        }
    }

    private var icon: Icon? = null
        set(value) {
            field = value

            val visibleImageView = if (ivIcon.isVisible) ivIcon else ivImage

            value?.let {
                IconRendererFactory.create(
                    icon = it,
                    urlIconShape = UrlIconShape.SUPER_ELLIPSE,
                    placeholder = iconPlaceholder
                )
            }
                ?.render(visibleImageView)
                ?: visibleImageView.clearSrc()
        }

    val ivImage: AppCompatImageView by lazy {
        AppCompatImageView(context)
            .apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            }
    }

    val ivIcon: AppCompatImageView by lazy {
        AppCompatImageView(context)
            .apply {
                val dp4 = 4.dp(context)
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                setPadding(dp4, dp4, dp4, dp4)
            }
    }

    var iconBackgroundColor: Int = resources.provideColor(R.color.print30)
        set(value) {
            field = if (value != -1) value else resources.provideColor(R.color.print30)
            updateIconBackground()
        }

    init {
        addView(ivImage)
        addView(ivIcon)
    }

    fun show(icon: ItemIconPresModel) {
        ivIcon goneIf (icon !is ItemConstantIconPresModel)
        ivImage goneIf (icon is ItemConstantIconPresModel)

        when (icon) {
            is ItemDefaultIconPresModel -> {
                this.icon = null
            }
            is ItemConstantIconPresModel -> {
                this.icon = icon.icon
                setIconColor(icon.iconColor)
                iconBackgroundColor = icon.backgroundColor

            }
            is ItemUrlIconPresModel -> {
                this.icon = UrlIcon(icon.url)
            }
        }

    }

    private fun setIconColor(@ColorInt color: Int) {
        if (color != -1) {
            icon?.let {
                if (it is PaintedIcon) icon = it.toColor(context, color)
            }
        }
    }

    private fun updateIconBackground() {
        ivIcon.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = iconCornerRadiusDp
            setColor(iconBackgroundColor)
        }
    }

}