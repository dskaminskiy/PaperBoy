package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper

class AuthPhonePresenter(
    view: AuthPhoneView,
    private val presentMapper: Mapper<AuthPhone, AuthPhonePresentModel> =
        AuthPhoneToPresentMapper()
) : BasePresenterImpl<AuthPhoneView>(view) {

    companion object {
        private const val MAX_LENGTH_REIGN_NUMBER = 3
        private const val MAX_LENGTH_PHONE_NUMBER = 10
    }

    var authPhone = AuthPhone.EMPTY

    var reignNumber: Int
        set(value) {
            authPhone = authPhone.copy(region = value)
            updateView()
        }
        get() = authPhone.region

    var phoneNumber: Long
        set(value) {
            authPhone = authPhone.copy(phone = value)
            updateView()
        }
        get() = authPhone.phone

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        //TODO: Понять какой нужен номер региона и подставить его, пока русский
        reignNumber = 7
    }

    private fun updateView() {
        view?.show(presentMapper.map(authPhone))
    }

    fun onNextClick() {
        view?.showAuthCode()
    }

    /**
     * @param newNumber     - номер региона с приставкой "+" (пример: "+7")
     */
    fun onReignAdditionalNumberChanged(newNumber: String) {
        if (newNumber.isNotEmpty() && newNumber != "+") {
            val clearNumber = newNumber.takeIf { it.length > 2 }
                ?.drop(1)
                ?: newNumber

            try {
                reignNumber = clearNumber.toInt()
                if (clearNumber.length > MAX_LENGTH_REIGN_NUMBER) throw java.lang.NumberFormatException()
            } catch (e: NumberFormatException) {
                onReignAdditionalNumberChanged(newNumber.dropLast(1))
            }
        } else {
            reignNumber = -1
        }
    }

    /**
     * @param newNumber     - номер телефона с пробелами (пример: "977 804 05 76")
     */
    fun onPhoneNumberChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            val clearNumber = newNumber.trim()

            try {
                phoneNumber = clearNumber.toLong()
                if (clearNumber.length > MAX_LENGTH_PHONE_NUMBER) throw java.lang.NumberFormatException()
            } catch (e: NumberFormatException) {
                onPhoneNumberChanged(newNumber.dropLast(1))
            }
        } else {
            phoneNumber = -1
        }
    }

}

class AuthPhoneToPresentMapper : Mapper<AuthPhone, AuthPhonePresentModel> {
    override fun map(from: AuthPhone): AuthPhonePresentModel =
        AuthPhonePresentModel(
            regionAdditionalNumber = from.region.takeIf { it != -1 }?.toReignFormat() ?: "+",
            phoneNumber = from.phone.takeIf { it != -1L }?.toFormatNumber() ?: ""
        )

    private fun Long.toFormatNumber(): String = this.toString()
    private fun Int.toReignFormat(): String = "+$this"

}