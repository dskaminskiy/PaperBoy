package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.ColorsFactory
import kotlinx.android.synthetic.main.view_input.view.*


class InputView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val CORNER_RADIUS = 4
        const val STROKE_WIDTH = 2
    }

    private val palette: Colors by lazy { ColorsFactory.create(context) }

    var text: String
        set(value) {
            tvText.text = value
        }
        get() = tvText.text.toString()

    var isStroke: Boolean = false
        set(value) {
            field = value
            background = defaultBackground.apply {
                setStroke(if (value) STROKE_WIDTH else 0, palette.print30)
            }
        }

    var textColor: Int
        set(value) {
            if (value != -1) tvText.setTextColor(value)
        }
        get() = tvText.currentTextColor

    private val defaultBackground: GradientDrawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = CORNER_RADIUS.toFloat()
            setColor(palette.print90)
        }
    }

    init {
        View.inflate(context, R.layout.view_input, this)
    }

    fun show(model: InputViewPresentModel) {
        text = model.text
        isStroke = model.isStroke
        isEnabled = model.isEnabled
    }

    private fun updatePaddings(textType: InputViewTextType) {

    }

    private fun updateTextSize(textType: InputViewTextType) {

    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled) textColor = palette.paper else palette.print50
    }

}

data class InputViewPresentModel(
    val text: String,
    val isEnabled: Boolean,
    val isStroke: Boolean,
    val textType: InputViewTextType
)

enum class InputViewTextType(
    val textSize: Int
) {
    PASS_CODE(24),
    PHONE_NUMBER(20)
}