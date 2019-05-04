package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.data.settings.ApplicationSettings
import deniskaminskiy.paperboy.data.settings.ApplicationSettingsImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface AuthSecurityCodeInteractor : Interactor {

    fun onSecurityCodeTextChanged(newCode: String)

    fun onUiUpdateRequest(): Observable<Boolean>

    fun sendSecurityCode(): Observable<AuthResponseState>

}

class AuthSecurityCodeInteractorImpl(
    private val contextDelegate: ContextDelegate,
    private val settings: ApplicationSettings = ApplicationSettingsImpl(contextDelegate),
    private val repository: AuthRepository = AuthRepositoryFactory.create()
) : AuthSecurityCodeInteractor {

    private var securityCode: String = ""

    private val updateSubject = BehaviorSubject.createDefault(securityCode.isNotBlank())

    override fun onSecurityCodeTextChanged(newCode: String) {
        securityCode = newCode
        updateSubject.onNext(newCode.isNotBlank())
    }

    override fun onUiUpdateRequest(): Observable<Boolean> = updateSubject

    override fun sendSecurityCode(): Observable<AuthResponseState> =
        repository.sendSecurityCode(securityCode, settings.userToken)


}