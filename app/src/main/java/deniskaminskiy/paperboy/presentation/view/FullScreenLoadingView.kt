package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.compatColor
import kotlinx.android.synthetic.main.view_loading.view.*

class FullScreenLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_loading, this)

        vProgress.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(context.compatColor(R.color.paper))
        }
    }

}