package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.ColorsFactory
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.icon.Icon
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.icon.IconRendererFactory
import deniskaminskiy.paperboy.utils.icon.PaintedIcon
import kotlinx.android.synthetic.main.view_top_popup.view.*

class TopPopupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val CORNER_RADIUS = 12
    }

    var title: String
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.text = value
        }

    var titleColor: Int
        get() = tvTitle.currentTextColor
        set(value) {
            if (value != -1) {
                tvTitle.setTextColor(value)
            }
        }

    var subtitle: String
        get() = tvSubtitle.text.toString()
        set(value) {
            tvSubtitle.text = value
        }

    var subtitleColor: Int
        get() = tvSubtitle.currentTextColor
        set(value) {
            if (value != -1) {
                tvSubtitle.setTextColor(value)
            }
        }

    var icon: Icon? = null
        set(value) {
            field = value
            value?.let { IconRendererFactory.create(it) }
                ?.render(ivIcon)
        }

    private val palette: Colors by lazy { ColorsFactory.create(context) }

    private val dpCornerRadius = CORNER_RADIUS.dp(context).toFloat()

    private val defaultBackground by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setColor(palette.print100)
        }
    }

    init {
        View.inflate(context, R.layout.view_top_popup, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.TopPopupView, 0, 0)

        show(
            TopPopupPresentModel(
                title = a.getString(R.styleable.TopPopupView_title) ?: "",
                titleColor = a.getColor(R.styleable.TopPopupView_titleColor, palette.paper),
                subtitle = a.getString(R.styleable.TopPopupView_subtitle) ?: "",
                subtitleColor = a.getColor(R.styleable.TopPopupView_subtitleColor, palette.print40),
                icon = a.getInt(R.styleable.TopPopupView_iconConstant, -1)
                    .takeIf { it != -1 }?.let(IconFactory::create),
                iconColor = a.getInt(R.styleable.TopPopupView_iconColor, palette.marlboroNew),
                backgroundColor = a.getColor(R.styleable.TopPopupView_backgroundColor, palette.print100)
            )
        )

        a.recycle()
    }

    fun show(model: TopPopupPresentModel) {
        title = model.title
        subtitle = model.subtitle
        titleColor = model.titleColor
        subtitleColor = model.subtitleColor
        icon = model.icon
        setIconColor(model.iconColor)
        setBackgroundColor(model.backgroundColor)
    }


    fun setIconColor(@ColorInt color: Int) {
        if (color != -1) {
            icon?.let {
                if (it is PaintedIcon) icon = it.toColor(context, color)
            }
        }
    }

    override fun setBackgroundColor(color: Int) {
        if (color != -1) {
            background = defaultBackground.apply {
                setColor(color)
            }
        }
    }

}

data class TopPopupPresentModel(
    val title: String,
    val subtitle: String = "",
    @ColorInt val titleColor: Int = -1,
    @ColorInt val subtitleColor: Int = -1,
    val icon: Icon? = null,
    @ColorInt val iconColor: Int = -1,
    @ColorInt val backgroundColor: Int = -1
)