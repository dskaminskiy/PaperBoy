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
        const val ICON_CORNER_RADIUS = 6

        const val EXTRA_TITLE_CORNER_RADIUS = 4
        const val EXTRA_TITLE_STROKE_WIDTH = 1
    }

    private val palette: ResourcesManager.Colors by lazy { AndroidResourcesManager.create(context).colors }

    private val extraTitleStrokeWidthDp = EXTRA_TITLE_STROKE_WIDTH.dp(context)
    private val extraTitleCornerRadiusDp = EXTRA_TITLE_CORNER_RADIUS.dp(context).toFloat()

    private val iconCornerRadiusDp = ICON_CORNER_RADIUS.dp(context).toFloat()

    private val iconPlaceholder: Drawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = iconCornerRadiusDp
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

            val visibleImageView = if (ivIcon.isVisible) ivIcon else ivImage

            value?.let {
                IconRendererFactory.create(
                    icon = it,
                    urlIconShape = UrlIconShape.SUPER_ELLIPSE,
                    placeholder = iconPlaceholder
                )
            }
                ?.render(visibleImageView)
                ?: visibleImageView.setImageDrawable(null)
        }

    var iconBackgroundColor: Int = palette.print30
        set(value) {
            field = if (value != -1) value else palette.print30
            updateIconBackground()
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
            cornerRadius = extraTitleCornerRadiusDp
            setStroke(extraTitleStrokeWidthDp, palette.print15)
        }

        updateIconBackground()
    }

    fun show(model: MiddleItemPresentModel) {
        title = model.title
        subtitle = model.subtitle
        extraTitle = model.extraTitle

        isDivier = model.isDivider

        ivIcon goneIf (model.icon !is MiddleItemIconConstant)
        ivImage goneIf (model.icon is MiddleItemIconConstant)

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

    private fun updateIconBackground() {
        ivIcon.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = iconCornerRadiusDp
            setColor(iconBackgroundColor)
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