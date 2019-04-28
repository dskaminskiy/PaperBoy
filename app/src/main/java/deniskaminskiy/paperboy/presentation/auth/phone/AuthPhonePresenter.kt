package deniskaminskiy.paperboy.presentation.auth.phone

import android.content.Context
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractor
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractorImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.disposeIfNotNull
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.Disposable

class AuthPhonePresenter(
    view: AuthPhoneView,
    private val contextDelegate: ContextDelegate,
    private val interactor: AuthPhoneInteractor = AuthPhoneInteractorImpl(),
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<AuthPhoneView>(view) {

    companion object {
        private const val MAX_LENGTH_REIGN_NUMBER = 3
        private const val MAX_LENGTH_PHONE_NUMBER = 10

        private const val SETTINGS_FILE_NAME = "authSettings"
        private const val USER_TOKEN = "USER_TOKEN"
    }

    private var disposableCode: Disposable? = null


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

    override fun onViewDetached() {
        disposableCode.disposeIfNotNull()
        super.onViewDetached()
    }

    private fun updateView() {
        view?.show(
            AuthPhonePresentModel(
                regionAdditionalNumber = reignNumber.takeIf { it != -1 }?.toReignFormat() ?: "+",
                phoneNumber = phoneNumber.takeIf { it != -1L }?.toFormatNumber() ?: "",
                isNextButtonEnable = phoneNumber.toString().length == MAX_LENGTH_PHONE_NUMBER
                        && reignNumber.toString().isNotEmpty() && reignNumber != -1
            )
        )
    }

    fun onNextClick() {
        disposableCode = interactor.requestCode(reignNumber, phoneNumber)
            .compose(composer.observable())
            .subscribe {
                contextDelegate.getContext()?.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)?.edit()
                    ?.let { prefs ->
                        prefs.putString(USER_TOKEN, it.token)
                        prefs.apply()
                    }

                view?.showAuthCode()
            }
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

    private fun Long.toFormatNumber(): String = this.toString()
    private fun Int.toReignFormat(): String = "+$this"

}