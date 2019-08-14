package deniskaminskiy.paperboy.core

import androidx.annotation.UiThread
import deniskaminskiy.paperboy.presentation.view.TopPopupPresModel

@UiThread
interface View {

    fun showTopPopup(model: TopPopupPresModel, animListener: OnAnimationLifecycleListener? = null)

}