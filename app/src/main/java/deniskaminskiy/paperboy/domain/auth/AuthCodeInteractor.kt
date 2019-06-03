package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.presentation.intro.choose.LoadImportChannelsInteractor
import deniskaminskiy.paperboy.presentation.intro.choose.LoadImportChannelsInteractorImpl
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface AuthCodeInteractor : Interactor {

    var isChannelsFetched: Boolean

    var onFullCodeEntered: () -> Unit

    fun onModelUpdate(): Observable<String>

    fun sendCode(): Observable<AuthResponseState>

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String)

    fun clearCode()

    fun removeLastCodeSymbol()

    fun loadAndCacheImportChannels(): Completable

}

class AuthCodeInteractorImpl(
    private val codeLength: Int,
    private val repository: AuthRepository = AuthRepositoryFactory.create(),
    private val loadImportChannelsInteractor: LoadImportChannelsInteractor = LoadImportChannelsInteractorImpl()
) : AuthCodeInteractor {

    private val subjectModel = BehaviorSubject.createDefault("")

    private var code: String
        get() = subjectModel.value ?: ""
        set(value) {
            subjectModel.onNext(value)
        }

    override var isChannelsFetched: Boolean = false

    override var onFullCodeEntered: () -> Unit = {}

    override fun onModelUpdate(): Observable<String> = subjectModel

    override fun sendCode(): Observable<AuthResponseState> = repository.sendCode(code.toInt())

    override fun onPassCodeChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            code += newNumber
        } else {
            if (code.isNotEmpty()) {
                code = code.dropLast(1)
            }
        }

        if (code.length >= codeLength) {
            onFullCodeEntered.invoke()
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

    override fun loadAndCacheImportChannels(): Completable = loadImportChannelsInteractor.loadAndCache()
        .doOnComplete { isChannelsFetched = true }

}