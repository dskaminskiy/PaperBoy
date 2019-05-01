package deniskaminskiy.paperboy.presentation.auth.security

import deniskaminskiy.paperboy.core.View

interface AuthSecurityCodeView : View {

    fun show(isNextButtonEnable: Boolean)

    fun showLoading()

    fun close()

    fun showImportChannels()

}