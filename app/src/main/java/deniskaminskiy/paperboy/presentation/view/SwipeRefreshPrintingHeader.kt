package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.reginald.swiperefresh.CustomSwipeRefreshLayout

class SwipeRefreshPrintingHeader(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CustomSwipeRefreshLayout.CustomSwipeRefreshHeadLayout {

    override fun onStateChange(
        currentState: CustomSwipeRefreshLayout.State?,
        lastState: CustomSwipeRefreshLayout.State?
    ) {
    }

}