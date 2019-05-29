package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.LinearLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.view.addOnTextChangedListener
import kotlinx.android.synthetic.main.view_number_input.view.*

typealias OnTextChanged = (String) -> Unit
typealias OnFocusChanged = (Boolean) -> Unit
typealias OnBackspacePressedWithEmptyText = () -> Unit

class NumberInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val CORNER_RADIUS = 4
        const val STROKE_WIDTH = 2
    }

    var onTextChanged: OnTextChanged = {}
        set(value) {
            field = value
            etText.addOnTextChangedListener(value)
        }

    var onBackspacePressedWithEmptyText: OnBackspacePressedWithEmptyText = {}
    var onFocusChanged: OnFocusChanged = {}

    var wasMistake = false

    private val palette: ResourcesManager.Colors by lazy { AndroidResourcesManager.create(context).colors }

    var text: String
        set(value) {
            if (etText.text.toString() != value) {
                etText.setText(value)
                setSelectionToEnd()
            }

            if (wasMistake) {
                wasMistake = false
                isStroke = true
            }
        }
        get() = etText.text.toString()

    var isStroke: Boolean = false
        set(value) {
            field = value
            background = defaultBackground.apply {
                setStroke(dpStrokeWidth, when {
                    wasMistake -> palette.marlboroNew
                    value -> palette.admiral
                    else -> Color.TRANSPARENT
                })
            }
            requestLayout()
        }

    private val defaultBackground: GradientDrawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setColor(palette.print15)
        }
    }

    var hintText: String
        get() = etText.hint.toString()
        set(value) {
            etText.hint = value
        }

    private val dpStrokeWidth = STROKE_WIDTH.dp(context)
    private val dpCornerRadius = CORNER_RADIUS.dp(context).toFloat()

    init {
        View.inflate(context, R.layout.view_number_input, this)

        // При фокусе - показывать обводку и ставить курсор в конец
        etText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            isStroke = hasFocus
            onFocusChanged(hasFocus)
            if (hasFocus) setSelectionToEnd()
        }

        setOnClickListener { v ->
            etText.requestFocus()
        }

        setOnFocusChangeListener { v, hasFocus ->
            etText.requestFocus()
        }

        // Отправляем ивент о попытке стереть пустую строку
        etText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL && text.isEmpty()) {
                onBackspacePressedWithEmptyText.invoke()
            }
            false
        }

        etText.transformationMethod = object : TransformationMethod {
            override fun getTransformation(source: CharSequence, view: View): CharSequence = source

            override fun onFocusChanged(
                view: View?,
                sourceText: CharSequence?,
                focused: Boolean,
                direction: Int,
                previouslyFocusedRect: Rect?
            ) {

            }

        }

        background = defaultBackground
    }

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.NumberInputView, 0, 0)

        hintText = a.getString(R.styleable.NumberInputView_hintText) ?: ""

        a.recycle()
    }

    fun setSelectionToEnd() {
        etText.post { etText.setSelection(etText.length(), etText.length()) }
    }

    fun showError() {
        wasMistake = true
        isStroke = true
        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }

}