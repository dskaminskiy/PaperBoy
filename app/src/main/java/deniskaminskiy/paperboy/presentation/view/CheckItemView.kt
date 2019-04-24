package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.ColorsFactory
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.view.goneIf
import deniskaminskiy.paperboy.utils.view.isVisible
import kotlinx.android.synthetic.main.view_check_item.view.*

class CheckItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val EXTRA_TITLE_CORNER_RADIUS = 4
        private const val EXTRA_TITLE_STROKE_WIDTH = 1
    }

    private val palette: Colors by lazy { ColorsFactory.create(context) }

    private val dpStrokeWidth = EXTRA_TITLE_STROKE_WIDTH.dp(context)
    private val dpCornerRadius = EXTRA_TITLE_CORNER_RADIUS.dp(context).toFloat()

    var title: String
        get() = tvTitle.text.toString()
        set(value) {
            tvTitle.text = value
        }

    var extraTitle: String
        get() = tvExtra.text.toString()
        set(value) {
            tvExtra.text = value
            tvExtra goneIf value.isBlank()
        }

    var isChecked: Boolean
        get() = vCheckBox.isChecked
        set(value) {
            vCheckBox.isChecked = value
        }

    var isDivider: Boolean
        get() = vDivider.isVisible
        set(value) {
            vDivider goneIf !value
        }

    init {
        View.inflate(context, R.layout.view_check_item, this)

        vgContent.setOnClickListener { this.performClick() }

        tvExtra.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = dpCornerRadius
            setStroke(dpStrokeWidth, palette.print15)
        }

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckItemView, 0, 0)

        show(
            CheckItemPresentModel(
                title = a.getString(R.styleable.CheckItemView_title) ?: "",
                extraTitle = a.getString(R.styleable.CheckItemView_extraTitle) ?: "",
                isChecked = a.getBoolean(R.styleable.CheckItemView_isChecked, false),
                isDivider = a.getBoolean(R.styleable.CheckItemView_isDivider, false)
            )
        )

        a.recycle()
    }

    fun show(model: CheckItemPresentModel) {
        title = model.title
        extraTitle = model.extraTitle
        isChecked = model.isChecked
        isDivider = model.isDivider
    }

}

data class CheckItemPresentModel(
    val title: String,
    val extraTitle: String = "",
    val isChecked: Boolean = false,
    val isDivider: Boolean = false
)