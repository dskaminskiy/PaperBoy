package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface AuthCodeInteractor : Interactor {

    var onFullCodeEntered: () -> Unit

    fun onModelUpdate(): Observable<String>

    fun sendCode(): Observable<AuthResponseState>

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String)

    fun clearCode()

    fun removeLastCodeSymbol()

}

class AuthCodeInteractorImpl(
    private val codeLength: Int,
    private val repository: AuthRepository = AuthRepositoryFactory.create()
) : AuthCodeInteractor {

    private val subjectModel = BehaviorSubject.createDefault("")

    private var code: String
        get() = subjectModel.value ?: ""
        set(value) {
            subjectModel.onNext(value)
        }

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

}