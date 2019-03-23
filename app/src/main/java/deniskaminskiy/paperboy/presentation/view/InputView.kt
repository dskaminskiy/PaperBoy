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
import kotlinx.android.synthetic.main.view_input.view.*

class InputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val CORNER_RADIUS = 4
        const val STROKE_WIDTH = 2
    }

    // variable name "colors" not available
    private val palette: Colors by lazy { ColorsFactory.create(context) }

    var text: String
        set(value) {
            tvText.setText(value)
        }
        get() = tvText.text.toString()

    var isStroke: Boolean = false
        set(value) {
            field = value
            background = defaultBackground.apply {
                setStroke(if (value) dpStrokeWidth else 0, palette.admiral)
            }
        }

    private val defaultBackground: GradientDrawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setColor(palette.print15)
        }
    }

    private val dpStrokeWidth = STROKE_WIDTH.dp(context)
    private val dpCornerRadius = CORNER_RADIUS.dp(context).toFloat()

    init {
        View.inflate(context, R.layout.view_input, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.InputView, 0, 0)

        show(
            InputViewPresentModel(
                text = a.getString(R.styleable.InputView_text) ?: "",
                isStroke = a.getBoolean(R.styleable.InputView_isStroke, false)
            )
        )

        a.recycle()
    }

    fun show(model: InputViewPresentModel) {
        text = model.text
        isStroke = model.isStroke
    }

}

data class InputViewPresentModel(
    val text: String,
    val isStroke: Boolean
)