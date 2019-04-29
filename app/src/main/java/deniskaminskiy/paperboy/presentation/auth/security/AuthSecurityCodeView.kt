package deniskaminskiy.paperboy.presentation.auth.security

import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel

interface AuthSecurityCodeView : View {

    fun show(isNextButtonEnable: Boolean)

    fun showLoading()

    fun close()

    fun showImportChannels()

    fun showError(model: TopPopupPresentModel)

}