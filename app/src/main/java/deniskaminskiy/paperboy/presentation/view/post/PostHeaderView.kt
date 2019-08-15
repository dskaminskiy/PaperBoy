package deniskaminskiy.paperboy.presentation.view.post

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.data.ItemDefaultIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemIconPresModel
import deniskaminskiy.paperboy.utils.getIcon
import deniskaminskiy.paperboy.utils.icon.IconAttrs
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesProvider
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.view.ViewTextColorDelegate
import deniskaminskiy.paperboy.utils.view.ViewTextDelegate
import deniskaminskiy.paperboy.utils.view.ViewVisibilityDelegate
import kotlinx.android.synthetic.main.view_post_header.view.*

class PostHeaderView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val resources: ResourcesProvider by lazy { AndroidResourcesProvider.create(context) }

    var title: String by ViewTextDelegate(R.id.tvTitle, false)
    var titleColor: Int by ViewTextColorDelegate(R.id.tvTitle, resources.provideColor(R.color.print100))

    var subtitle: String by ViewTextDelegate(R.id.tvSubtitle)
    var subtitleColor: Int by ViewTextColorDelegate(R.id.tvSubtitle, resources.provideColor(R.color.print70))

    var isDivider: Boolean by ViewVisibilityDelegate(R.id.vDivider)

    init {
        inflate(context, R.layout.view_post_header, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.PostHeaderView, 0, 0)

        show(
            PostHeaderPresModel(
                title = a.getString(R.styleable.PostHeaderView_title) ?: "",
                subtitle = a.getString(R.styleable.PostHeaderView_subtitle) ?: "",
                image = a.getIcon(
                    IconAttrs(
                        constant = R.styleable.PostHeaderView_leftIconConstant,
                        color = R.styleable.PostHeaderView_leftIconColor,
                        backgroundColor = R.styleable.PostHeaderView_leftIconBackgroundColor
                    )
                ),
                icon = a.getIcon(
                    IconAttrs(
                        constant = R.styleable.PostHeaderView_rightIconConstant,
                        color = R.styleable.PostHeaderView_rightIconColor,
                        backgroundColor = R.styleable.PostHeaderView_rightIconBackgroundColor
                    )
                ),
                isDivider = a.getBoolean(R.styleable.PostHeaderView_isDivider, true)
            )
        )

        a.recycle()
    }

    fun show(model: PostHeaderPresModel) {
        title = model.title
        subtitle = model.subtitle

        ivImage.show(model.image)
        ivIcon.show(model.icon)

        isDivider = model.isDivider
    }

}

data class PostHeaderPresModel(
    val title: String,
    val subtitle: String = "",
    val image: ItemIconPresModel = ItemDefaultIconPresModel,
    val icon: ItemIconPresModel = ItemDefaultIconPresModel,
    val isDivider: Boolean = false
)