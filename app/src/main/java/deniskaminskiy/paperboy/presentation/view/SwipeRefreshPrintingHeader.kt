package deniskaminskiy.paperboy.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.reginald.swiperefresh.CustomSwipeRefreshLayout
import com.reginald.swiperefresh.CustomSwipeRefreshLayout.State.*
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.view.invisible
import deniskaminskiy.paperboy.utils.view.isInvisible
import deniskaminskiy.paperboy.utils.view.isVisible
import deniskaminskiy.paperboy.utils.view.visible
import kotlinx.android.synthetic.main.view_swipe_refresh_printing_header.view.*

class SwipeRefreshPrintingHeader(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val offsetAnimTitle: Int = OFFSET_TITLE_DP.dp(context)
) : FrameLayout(context, attrs, defStyleAttr), CustomSwipeRefreshLayout.CustomSwipeRefreshHeadLayout {

    companion object {
        private const val OFFSET_TITLE_DP = 16
    }

    init {
        View.inflate(context, R.layout.view_swipe_refresh_printing_header, this)
    }

    override fun onStateChange(
        currentState: CustomSwipeRefreshLayout.State,
        lastState: CustomSwipeRefreshLayout.State
    ) {
        Log.d("Steve", "---------------------------------|")

        val stateCode = currentState.refreshState
        val lastStateCode = lastState.refreshState
        val percent = currentState.percent

        Log.d("Steve", "state: " + defineState(stateCode))
        Log.d("Steve", "lastState: " + defineState(lastStateCode))
        Log.d("Steve", "statePercent: " + currentState.percent)

        when (stateCode) {
            STATE_NORMAL -> {
                when(percent) {
                    in 0.5f..1f -> {
                        if (vTitle.isInvisible) vTitle.visible()
                        vTitle.translationY = - (offsetAnimTitle * 2) * (1 - percent)
                    }
                    else -> {
                        if (vTitle.isVisible) vTitle.invisible()
                    }
                }
            }
            STATE_READY -> {
                vTitle.visible()
                vTitle.translationY = 0f
            }
        }

        Log.d("Steve", "---------------------------------|")

    }

    //TODO: temp
    private fun defineState(stateCode: Int) = when (stateCode) {
        STATE_NORMAL -> "STATE_NORMAL"
        STATE_COMPLETE -> "STATE COMPLETE"
        STATE_READY -> "STATE READY"
        STATE_REFRESHING -> "STATE_REFRESHING"
        else -> "STATE_UNKNOWN"
    }

}