package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.icon.*
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.view.goneIf
import deniskaminskiy.paperboy.utils.view.invisibleIf
import deniskaminskiy.paperboy.utils.view.isVisible
import kotlinx.android.synthetic.main.view_middle_item.view.*

class MiddleItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val EXTRA_TITLE_CORNER_RADIUS = 8
        const val EXTRA_TITLE_STROKE_WIDTH = 1
    }

    private val palette: ResourcesManager.Colors by lazy { AndroidResourcesManager.create(context).colors }

    private val dpStrokeWidth = EXTRA_TITLE_STROKE_WIDTH.dp(context)
    private val dpCornerRadius = EXTRA_TITLE_CORNER_RADIUS.dp(context).toFloat()

    private val iconBackground: GradientDrawable
        get() =
            GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = dpCornerRadius
                setColor(iconBackgroundColor)
            }

    // Оранжевый сквиркл
    private val defaultIconPlaceholder: Drawable by lazy {
        GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(palette.print20, palette.print60)
        ).apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
        }
    }

    var title: String
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.text = value
        }

    var subtitle: String
        get() = tvSubtitle.text.toString()
        set(value) {
            tvSubtitle.text = value
            tvSubtitle goneIf value.isBlank()
        }

    var extraTitle: String
        get() = tvExtra.text.toString()
        set(value) {
            tvExtra.text = value
            tvExtra goneIf value.isBlank()
        }

    var isDivier: Boolean
        get() = vDivider.isVisible
        set(value) {
            vDivider invisibleIf !value
        }

    var icon: Icon? = null
        set(value) {
            field = value

            val padding = if (value is ConstantIcon) 12.dp(context) else 0
            ivIcon.setPadding(padding, padding, padding, padding)

            value?.let {
                IconRendererFactory.create(
                    icon = it,
                    urlIconShape = UrlIconShape.SUPER_ELLIPSE,
                    placeholder = defaultIconPlaceholder
                )
            }
                ?.render(ivIcon)
                ?: ivIcon.setImageDrawable(null)
        }

    var iconBackgroundColor: Int = palette.print30
        set(value) {
            field = if (value != -1) value else palette.print30
            ivIcon.background = iconBackground
        }

    init {
        View.inflate(context, R.layout.view_middle_item, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.MiddleItemView, 0, 0)

        show(
            MiddleItemPresentModel(
                title = a.getString(R.styleable.MiddleItemView_title) ?: "",
                subtitle = a.getString(R.styleable.MiddleItemView_subtitle) ?: "",
                extraTitle = a.getString(R.styleable.MiddleItemView_extraTitle) ?: "",
                isDivider = a.getBoolean(R.styleable.MiddleItemView_isDivider, true),
                icon = MiddleItemIconConstant(
                    icon = a.getInt(R.styleable.MiddleItemView_iconConstant, -1)
                        .takeIf { it != -1 }?.let(IconFactory::create),
                    iconColor = a.getInt(R.styleable.MiddleItemView_iconColor, -1),
                    backgroundColor = a.getInt(R.styleable.MiddleItemView_iconBackgroundColor, -1)
                )
            )
        )

        a.recycle()
    }

    init {
        tvExtra.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setStroke(dpStrokeWidth, palette.print15)
        }

        ivIcon.background = iconBackground
    }

    fun show(model: MiddleItemPresentModel) {
        title = model.title
        subtitle = model.subtitle
        extraTitle = model.extraTitle

        isDivier = model.isDivider

        when (model.icon) {
            is MiddleItemIconDefault -> {
                icon = null
            }
            is MiddleItemIconConstant -> {
                icon = model.icon.icon
                setIconColor(model.icon.iconColor)
                iconBackgroundColor = model.icon.backgroundColor

            }
            is MiddleItemIconUrl -> {
                icon = UrlIcon(model.icon.url)
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

}

data class MiddleItemPresentModel(
    val title: String,
    val subtitle: String = "",
    val extraTitle: String = "",
    val isDivider: Boolean = true,
    val icon: MiddleItemIcon = MiddleItemIconDefault
)

sealed class MiddleItemIcon

object MiddleItemIconDefault : MiddleItemIcon()

data class MiddleItemIconConstant(
    val icon: ConstantIcon? = null,
    @ColorInt val iconColor: Int = -1,
    @ColorInt val backgroundColor: Int = -1
) : MiddleItemIcon()

data class MiddleItemIconUrl(
    val url: String
) : MiddleItemIcon()