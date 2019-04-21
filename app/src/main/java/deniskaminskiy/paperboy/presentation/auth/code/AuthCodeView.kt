package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.View

interface AuthCodeView : View {

    fun show(model: AuthCodePresentModel)

    fun close()

    fun showAuthSecurityCode()

    fun showImportChannels()

    fun showSmsSended()

}

data class AuthCodePresentModel(
    val code: String
)