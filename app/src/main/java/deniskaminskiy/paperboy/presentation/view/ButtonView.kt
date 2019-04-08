package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.ColorsFactory
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.icon.Icon
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.icon.IconRendererFactory
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

    private val palette: Colors by lazy { ColorsFactory.create(context) }

    private val dpCornerRadius = CORNER_RADIUS.dp(context).toFloat()

    init {
        View.inflate(context, R.layout.view_button, this)

        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setColor(palette.print100)
        }

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ButtonView, 0, 0)

        show(
            ButtonPresentModel(
                title = a.getString(R.styleable.ButtonView_title) ?: "",
                leftIcon = a.getInt(R.styleable.ButtonView_leftIconConstant, -1)
                    .takeIf { it != -1 }?.let(IconFactory::create),
                rightIcon = a.getInt(R.styleable.ButtonView_rightIconConstant, -1)
                    .takeIf { it != -1 }?.let(IconFactory::create)
            )
        )

        a.recycle()
    }

    fun show(model: ButtonPresentModel) {
        title = model.title
        leftIcon = model.leftIcon
        rightIcon = model.rightIcon
    }

}

data class ButtonPresentModel(
    val title: String,
    val leftIcon: Icon? = null,
    val rightIcon: Icon? = null
)