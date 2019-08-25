package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.data.importchannel.ImportChannelsRepository
import deniskaminskiy.paperboy.data.importchannel.ImportChannelsRepositoryImpl
import deniskaminskiy.paperboy.data.settings.PreferenceRepository
import deniskaminskiy.paperboy.data.settings.PreferenceRepositoryImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface AuthSecurityCodeInteractor : Interactor {

    fun onSecurityCodeTextChanged(newCode: String)

    fun onUiUpdateRequest(): Observable<Boolean>

    fun sendSecurityCode(): Observable<AuthResponseState>

    fun loadAndCacheImportChannels(): Completable

}

class AuthSecurityCodeInteractorImpl(
    private val contextDelegate: ContextDelegate,
    private val settings: PreferenceRepository = PreferenceRepositoryImpl(contextDelegate),
    private val repositoryAuth: AuthRepository = AuthRepositoryFactory.create(),
    private val repositoryImportChannels: ImportChannelsRepository = ImportChannelsRepositoryImpl()
) : AuthSecurityCodeInteractor {

    private var securityCode: String = ""

    private val updateSubject = BehaviorSubject.createDefault(securityCode.isNotBlank())

    override fun onSecurityCodeTextChanged(newCode: String) {
        securityCode = newCode
        updateSubject.onNext(newCode.isNotBlank())
    }

    override fun onUiUpdateRequest(): Observable<Boolean> = updateSubject

    override fun sendSecurityCode(): Observable<AuthResponseState> =
        repositoryAuth.sendSecurityCode(securityCode, settings.userToken)

    override fun loadAndCacheImportChannels(): Completable = repositoryImportChannels.getFromCloud()
        .flatMapCompletable(repositoryImportChannels::retain)

}