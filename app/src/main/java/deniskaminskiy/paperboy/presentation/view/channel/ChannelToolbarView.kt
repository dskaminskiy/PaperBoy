package deniskaminskiy.paperboy.presentation.view.channel

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.data.ItemConstantIconPresModel
import deniskaminskiy.paperboy.utils.getIcon
import deniskaminskiy.paperboy.utils.icon.Icon
import deniskaminskiy.paperboy.utils.icon.IconAttrs
import deniskaminskiy.paperboy.utils.icon.PaintedIcon
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesProvider
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.view.ViewIconDelegate
import deniskaminskiy.paperboy.utils.view.ViewTextDelegate
import deniskaminskiy.paperboy.utils.view.ViewVisibilityDelegate
import kotlinx.android.synthetic.main.view_channel_toolbar.view.*

class ChannelToolbarView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val resources: ResourcesProvider by lazy { AndroidResourcesProvider.create(context) }

    var title: String by ViewTextDelegate(tvTitle.id)
    var subtitle: String by ViewTextDelegate(tvSubtitle.id)
    var iconBack: Icon? by ViewIconDelegate(ivBack.id)
    var iconMore: Icon? by ViewIconDelegate(ivMore.id, true)
    var isDivider: Boolean by ViewVisibilityDelegate(vDivider.id)

    init {
        inflate(context, R.layout.view_channel_toolbar, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ChannelToolbarView, 0, 0)

        show(
            ChannelToolbarPresModel(
                title = a.getString(R.styleable.ChannelToolbarView_title) ?: "",
                subtitle = a.getString(R.styleable.ChannelToolbarView_subtitle) ?: "",
                iconBack = a.getIcon(
                    IconAttrs(
                        constant = R.styleable.ChannelToolbarView_leftIconConstant,
                        color = R.styleable.ChannelToolbarView_leftIconColor,
                        backgroundColor = null
                    )
                ),
                iconMore = a.getIcon(
                    IconAttrs(
                        constant = R.styleable.ChannelToolbarView_rightIconConstant,
                        color = R.styleable.ChannelToolbarView_rightIconColor,
                        backgroundColor = null
                    )
                )
            )
        )

        a.recycle()
    }

    fun show(model: ChannelToolbarPresModel) {
        title = model.title
        subtitle = model.subtitle
        iconBack = model.iconBack.icon
        setIconBackColor(model.iconBack.iconColor)
        iconMore = model.iconMore?.icon
        model.iconMore?.iconColor?.let(::setIconMoreColor)
        isDivider = model.isDivider
    }

    fun setIconBackColor(@ColorInt color: Int) {
        if (color != -1) {
            iconBack?.let {
                if (it is PaintedIcon) iconBack = it.toColor(context, color)
            }
        }
    }

    fun setIconMoreColor(@ColorInt color: Int) {
        if (color != -1) {
            iconMore?.let {
                if (it is PaintedIcon) iconMore = it.toColor(context, color)
            }
        }
    }

}

data class ChannelToolbarPresModel(
    val title: String,
    val subtitle: String = "",
    val iconBack: ItemConstantIconPresModel,
    val iconMore: ItemConstantIconPresModel? = null,
    val isDivider: Boolean = true
)