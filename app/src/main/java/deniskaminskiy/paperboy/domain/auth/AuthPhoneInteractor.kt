package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.data.settings.PreferenceRepository
import deniskaminskiy.paperboy.data.settings.PreferenceRepositoryImpl
import deniskaminskiy.paperboy.presentation.auth.phone.AuthPhone
import deniskaminskiy.paperboy.utils.ContextDelegate
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface AuthPhoneInteractor : Interactor {

    fun onModelUpdate(): Observable<AuthPhone>

    fun requestCode(): Observable<AuthResponseState>

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
    private val settings: PreferenceRepository = PreferenceRepositoryImpl(contextDelegate)
) : AuthPhoneInteractor {

    private val subjectModel = BehaviorSubject.createDefault(AuthPhone.EMPTY)

    private var region: Int
        set(value) {
            subjectModel.value
                ?.copy(region = value)
                ?.let(subjectModel::onNext)
        }
        get() = subjectModel.value?.region ?: -1

    private var phone: Long
        set(value) {
            subjectModel.value
                ?.copy(phone = value)
                ?.let(subjectModel::onNext)
        }
        get() = subjectModel.value?.phone ?: -1


    init {
        region = 7
    }

    override fun onModelUpdate(): Observable<AuthPhone> = subjectModel

    override fun requestCode(): Observable<AuthResponseState> = repository
        .requestCode("+$region$phone")
        .doOnNext { settings.userToken = it.token }
        .map { it.state }

    override fun onReignAdditionalNumberChanged(newNumber: String) {
        if (newNumber.isNotEmpty() && newNumber != "+") {
            val clearNumber = newNumber.takeIf { it.length > 2 }
                ?.drop(1)
                ?: newNumber

            try {
                region = clearNumber.toInt()
                if (clearNumber.length > maxLengthReign) throw java.lang.NumberFormatException()
            } catch (e: NumberFormatException) {
                onReignAdditionalNumberChanged(newNumber.dropLast(1))
            }
        } else {
            region = -1
        }
    }

    override fun onPhoneNumberChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            val clearNumber = newNumber.trim()

            try {
                phone = clearNumber.toLong()
                if (clearNumber.length > maxLengthPhone) throw java.lang.NumberFormatException()
            } catch (e: NumberFormatException) {
                onPhoneNumberChanged(newNumber.dropLast(1))
            }
        } else {
            phone = -1
        }
    }

}