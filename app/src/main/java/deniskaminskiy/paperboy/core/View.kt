package deniskaminskiy.paperboy.core

import androidx.annotation.UiThread
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel

@UiThread
interface View {

    fun showTopPopup(model: TopPopupPresentModel, animListener: OnAnimationLifecycleListener? = null)

}