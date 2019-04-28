package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel

interface AuthCodeView : View {

    fun show(model: AuthCodePresentModel)

    fun showError(model: TopPopupPresentModel)

    fun showLoading()

    fun close()

    fun showAuthSecurityCode()

    fun showImportChannels()

    fun showSmsSended()

}

data class AuthCodePresentModel(
    val code: String
)