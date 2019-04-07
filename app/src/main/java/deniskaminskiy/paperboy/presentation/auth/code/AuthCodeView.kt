package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.View

interface AuthCodeView : View {

    fun show(model: AuthCodePresentModel)

    fun close()

    fun showImportChannels()

}

data class AuthCodePresentModel(
    val code: String
)