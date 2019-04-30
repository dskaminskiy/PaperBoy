package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.auth.Auth
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.data.settings.ApplicationSettings
import deniskaminskiy.paperboy.data.settings.ApplicationSettingsImpl
import deniskaminskiy.paperboy.presentation.auth.phone.AuthPhone
import deniskaminskiy.paperboy.utils.ContextDelegate
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface AuthPhoneInteractor : Interactor {

    fun onUiUpdateRequest(): Observable<AuthPhone>

    fun requestCode(): Completable

    /**
     * @param newNumber     - номер региона с приставкой "+" (пример: "+7")
     */
    fun onReignAdditionalNumberChanged(newNumber: String)

    /**
     * @param newNumber     - номер телефона с пробелами (пример: "977 804 05 76")
     */
    fun onPhoneNumberChanged(newNumber: String)

}

class AuthPhoneInteractorImpl(
    private val maxLengthReign: Int,
    private val maxLengthPhone: Int,
    private val contextDelegate: ContextDelegate,
    private val repository: AuthRepository = AuthRepositoryFactory.create(),
    private val settings: ApplicationSettings = ApplicationSettingsImpl(contextDelegate)
) : AuthPhoneInteractor {

    private val updateSubject = BehaviorSubject.createDefault(AuthPhone.EMPTY)


    private var authPhone = AuthPhone.EMPTY

    private var reignNumber: Int
        set(value) {
            authPhone = authPhone.copy(region = value)
            updateUi()
        }
        get() = authPhone.region

    private var phoneNumber: Long
        set(value) {
            authPhone = authPhone.copy(phone = value)
            updateUi()
        }
        get() = authPhone.phone


    init {
        reignNumber = 7
    }

    private fun updateUi() {
        updateSubject.onNext(authPhone)
    }

    override fun onUiUpdateRequest(): Observable<AuthPhone> = updateSubject

    override fun requestCode(): Completable = repository
        .requestCode("+$reignNumber$phoneNumber")
        .flatMapCompletable {
            Completable.fromAction {
                settings.userToken = it.token
            }
        }

    override fun onReignAdditionalNumberChanged(newNumber: String) {
        if (newNumber.isNotEmpty() && newNumber != "+") {
            val clearNumber = newNumber.takeIf { it.length > 2 }
                ?.drop(1)
                ?: newNumber

            try {
                reignNumber = clearNumber.toInt()
                if (clearNumber.length > maxLengthReign) throw java.lang.NumberFormatException()
            } catch (e: NumberFormatException) {
                onReignAdditionalNumberChanged(newNumber.dropLast(1))
            }
        } else {
            reignNumber = -1
        }
    }

    override fun onPhoneNumberChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            val clearNumber = newNumber.trim()

            try {
                phoneNumber = clearNumber.toLong()
                if (clearNumber.length > maxLengthPhone) throw java.lang.NumberFormatException()
            } catch (e: NumberFormatException) {
                onPhoneNumberChanged(newNumber.dropLast(1))
            }
        } else {
            phoneNumber = -1
        }
    }

}