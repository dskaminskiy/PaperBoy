package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.View

interface AuthPhoneView : View {

    fun show(model: AuthPhonePresentModel)

    fun showLoading()

    fun hideLoading()

    fun showAuthCode()

}

data class AuthPhonePresentModel(
    val regionAdditionalNumber: String,
    val phoneNumber: String,
    val isNextButtonEnable: Boolean
)