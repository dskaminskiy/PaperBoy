package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.Mapper

class AuthPhoneToPresentModelMapper(
    private val maxLengthPhone: Int
) : Mapper<AuthPhone, AuthPhonePresentModel> {
    override fun map(from: AuthPhone): AuthPhonePresentModel =
        AuthPhonePresentModel(
            regionAdditionalNumber = from.region.takeIf { it != -1 }?.toReignFormat() ?: "+",
            phoneNumber = from.phone.takeIf { it != -1L }?.toFormatNumber() ?: "",
            isNextButtonEnable = from.phone.toString().length == maxLengthPhone
                    && from.region.toString().isNotEmpty() && from.region != -1
        )

    private fun Long.toFormatNumber(): String = this.toString()
    private fun Int.toReignFormat(): String = "+$this"
}