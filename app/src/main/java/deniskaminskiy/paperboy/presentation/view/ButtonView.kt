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
import kotlinx.android.synthetic.main.view_button.view.*

class ButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val CORNER_RADIUS = 12
    }

    var leftIcon: Icon? = null
        set(value) {
            field = value
            value?.let { IconRendererFactory.create(it) }
                ?.render(ivLeft)
        }

    var rightIcon: Icon? = null
        set(value) {
            field = value
            value?.let { IconRendererFactory.create(it) }
                ?.render(ivRight)
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
        View.inflate(context, R.layout.view_button, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ButtonView, 0, 0)

        show(
            ButtonPresentModel(
                title = a.getString(R.styleable.ButtonView_title) ?: "",
                titleColor = a.getColor(R.styleable.ButtonView_titleColor, palette.marlboroNew),
                leftIcon = a.getInt(R.styleable.ButtonView_leftIconConstant, -1)
                    .takeIf { it != -1 }?.let(IconFactory::create),
                leftIconColor = a.getColor(R.styleable.ButtonView_leftIconColor, palette.marlboroNew),
                rightIcon = a.getInt(R.styleable.ButtonView_rightIconConstant, -1)
                    .takeIf { it != -1 }?.let(IconFactory::create),
                rightIconColor = a.getColor(R.styleable.ButtonView_rightIconColor, palette.marlboroNew),
                backgroundColor = a.getColor(R.styleable.ButtonView_backgroundColor, palette.print100)
            )
        )

        a.recycle()
    }

    fun show(model: ButtonPresentModel) {
        title = model.title
        titleColor = model.titleColor

        leftIcon = model.leftIcon
        setLeftIconColor(model.leftIconColor)

        rightIcon = model.rightIcon
        setRightIconColor(model.rightIconColor)

        setBackgroundColor(model.backgroundColor)
    }

    /**
     * Важно: если иконка не типа [PaintedIcon] - то иконка не покрасится
     */
    fun setLeftIconColor(@ColorInt color: Int) {
        if (color != -1) {
            leftIcon?.let {
                if (it is PaintedIcon) leftIcon = it.toColor(context, color)
            }
        }
    }

    /**
     * Важно: если иконка не типа [PaintedIcon] - то иконка не покрасится
     */
    fun setRightIconColor(@ColorInt color: Int) {
        if (color != -1) {
            rightIcon?.let {
                if (it is PaintedIcon) rightIcon = it.toColor(context, color)
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

data class ButtonPresentModel(
    val title: String,
    @ColorInt
    val titleColor: Int = -1,
    val leftIcon: Icon? = null,
    @ColorInt
    val leftIconColor: Int = -1,
    val rightIcon: Icon? = null,
    @ColorInt
    val rightIconColor: Int = -1,
    @ColorInt
    val backgroundColor: Int = -1
)