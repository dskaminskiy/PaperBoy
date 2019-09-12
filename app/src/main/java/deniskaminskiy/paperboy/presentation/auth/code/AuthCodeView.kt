package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.View

interface AuthCodeView : View {

    fun show(model: String)

    fun showLoading()

    fun hideLoading()

    fun close()

    fun showAuthSecurityCode()

    fun showImportChannels()

    fun showHome()

    fun showSmsSended()

}

data class AuthCodePresModel(
    val code: String
)