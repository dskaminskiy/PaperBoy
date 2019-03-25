package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.View

interface AuthPhoneView : View {

    fun show(model: AuthPhonePresentModel)

}

data class AuthPhonePresentModel(
    val regionAdditionalNumber: String,
    val phoneNumber: String
)