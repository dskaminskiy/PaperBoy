package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.data.ItemConstantIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemDefaultIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemIconPresModel
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesProvider
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.view.goneIf
import deniskaminskiy.paperboy.utils.view.invisibleIf
import deniskaminskiy.paperboy.utils.view.isVisible
import kotlinx.android.synthetic.main.view_middle_item.view.*

class MiddleItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object {
        const val EXTRA_TITLE_CORNER_RADIUS = 4
        const val EXTRA_TITLE_STROKE_WIDTH = 1
    }

    private val resources: ResourcesProvider by lazy { AndroidResourcesProvider.create(context) }

    private val extraTitleStrokeWidthDp = EXTRA_TITLE_STROKE_WIDTH.dp(context)
    private val extraTitleCornerRadiusDp = EXTRA_TITLE_CORNER_RADIUS.dp(context).toFloat()

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

    init {
        View.inflate(context, R.layout.view_middle_item, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.MiddleItemView, 0, 0)

        show(
            MiddleItemPresModel(
                title = a.getString(R.styleable.MiddleItemView_title) ?: "",
                subtitle = a.getString(R.styleable.MiddleItemView_subtitle) ?: "",
                extraTitle = a.getString(R.styleable.MiddleItemView_extraTitle) ?: "",
                isDivider = a.getBoolean(R.styleable.MiddleItemView_isDivider, true),
                icon = ItemConstantIconPresModel(
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
            setStroke(extraTitleStrokeWidthDp, resources.provideColor(R.color.print15))
        }
    }

    fun show(model: MiddleItemPresModel) {
        title = model.title
        subtitle = model.subtitle
        extraTitle = model.extraTitle

        isDivier = model.isDivider

        ivFlip.show(model.icon)
    }

}

data class MiddleItemPresModel(
    val title: String,
    val subtitle: String = "",
    val extraTitle: String = "",
    val isDivider: Boolean = true,
    val icon: ItemIconPresModel = ItemDefaultIconPresModel
)