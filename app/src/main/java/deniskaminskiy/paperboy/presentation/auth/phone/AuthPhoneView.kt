package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.View

interface AuthPhoneView : View {

    fun show(model: AuthPhonePresModel)

    fun showLoading()

    fun hideLoading()

    fun showAuthCode()

    fun showImportChannels()

    fun showInputError()

}

data class AuthPhonePresModel(
    val regionAdditionalNumber: String,
    val phoneNumber: String,
    val isNextButtonEnable: Boolean
)