package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.data.ItemDefaultIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemIconPresModel
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.getIcon
import deniskaminskiy.paperboy.utils.icon.IconAttrs
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesProvider
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.view.ViewTextDelegate
import deniskaminskiy.paperboy.utils.view.ViewVisibilityDelegate
import deniskaminskiy.paperboy.utils.view.ViewVisibilityState
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

    var title: String by ViewTextDelegate(R.id.tvTitle, false)
    var subtitle: String by ViewTextDelegate(R.id.tvSubtitle)
    var extraTitle: String by ViewTextDelegate(R.id.tvExtra)
    var isDivider: Boolean by ViewVisibilityDelegate(R.id.vDivider, ViewVisibilityState.INVISIBLE)

    init {
        View.inflate(context, R.layout.view_middle_item, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.MiddleItemView, 0, 0)

        show(
            MiddleItemPresModel(
                title = a.getString(R.styleable.MiddleItemView_title) ?: "",
                subtitle = a.getString(R.styleable.MiddleItemView_subtitle) ?: "",
                extraTitle = a.getString(R.styleable.MiddleItemView_extraTitle) ?: "",
                isDivider = a.getBoolean(R.styleable.MiddleItemView_isDivider, true),
                icon = a.getIcon(
                    IconAttrs(
                        constant = R.styleable.MiddleItemView_iconConstant,
                        color = R.styleable.MiddleItemView_iconColor,
                        backgroundColor = R.styleable.MiddleItemView_iconBackgroundColor
                    )
                )
            )
        )

        a.recycle()
    }

    init {
        tvExtra.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = EXTRA_TITLE_CORNER_RADIUS.dp(context).toFloat()
            setStroke(EXTRA_TITLE_STROKE_WIDTH.dp(context), resources.provideColor(R.color.print15))
        }
    }

    fun show(model: MiddleItemPresModel) {
        title = model.title
        subtitle = model.subtitle
        extraTitle = model.extraTitle

        isDivider = model.isDivider

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