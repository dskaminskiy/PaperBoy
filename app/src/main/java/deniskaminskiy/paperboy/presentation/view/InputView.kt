package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.Color
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
            etText.setText(value)
        }
        get() = etText.text.toString()

    var isStroke: Boolean = false
        set(value) {
            field = value
            background = defaultBackground.apply {
                setStroke(dpStrokeWidth, if (value) palette.admiral else Color.TRANSPARENT)
            }
            //invalidate()
            //requestLayout()
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

        etText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            isStroke = hasFocus
        }

        setOnClickListener { v ->
            etText.requestFocus()
        }

        background = defaultBackground
    }

}