package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager

class DividerView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1.dp(context))
        setBackgroundColor(AndroidResourcesManager.create(context).colors.print20)
    }

}