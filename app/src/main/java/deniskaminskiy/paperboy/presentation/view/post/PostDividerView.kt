package deniskaminskiy.paperboy.presentation.view.post

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.compatColor
import deniskaminskiy.paperboy.utils.dp

class PostDividerView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    init {
        setBackgroundColor(context.compatColor(R.color.print20))
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1.dp(context))
    }

}