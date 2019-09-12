package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.data.importchannel.ImportChannelsRepository
import deniskaminskiy.paperboy.data.importchannel.ImportChannelsRepositoryFactory
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

interface AuthCodeInteractor : Interactor {

    fun onModelUpdate(): Observable<String>

    fun sendCode(): Observable<AuthResponseState>

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String)

    fun clearCode()

    fun removeLastCodeSymbol()

    /**
     * @return isAtLeastOneChannel  - true, если у пользователя имеется хотя бы 1 канал для длаьнейшего
     *                                импорта; false - если нет.
     */
    fun loadAndCacheImportChannels(): Single<Boolean>

}

class AuthCodeInteractorImpl(
    private val repositoryAuth: AuthRepository = AuthRepositoryFactory.create(),
    private val repositoryImportChannels: ImportChannelsRepository = ImportChannelsRepositoryFactory.create()
) : AuthCodeInteractor {

    companion object {
        private const val MAX_CODE_LENGTH = 5
    }

    private val subjectModel = BehaviorSubject.createDefault("")

    private var code: String
        get() = subjectModel.value ?: ""
        set(value) {
            subjectModel.onNext(value)
        }

    override fun onModelUpdate(): Observable<String> = subjectModel

    override fun sendCode(): Observable<AuthResponseState> = repositoryAuth.sendCode(code)

    override fun onPassCodeChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            code += newNumber
        } else {
            if (code.isNotEmpty()) {
                code = code.dropLast(1)
            }
        }
    }

    override fun clearCode() {
        code = ""
    }

    override fun removeLastCodeSymbol() {
        if (code.isNotEmpty()) {
            code = code.dropLast(1)
        }
    }

    override fun loadAndCacheImportChannels(): Single<Boolean> {
        var channelsCount = 0

        return repositoryImportChannels.getFromCloud()
            .doOnNext { channelsCount = it.size }
            .flatMapCompletable(repositoryImportChannels::retain)
            .toSingle {
               channelsCount != 0
            }
    }

}