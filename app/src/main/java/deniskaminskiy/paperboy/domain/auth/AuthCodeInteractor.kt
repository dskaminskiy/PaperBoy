package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface AuthCodeInteractor : Interactor {

    fun onUiUpdateRequest(): Observable<String>

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

    private var code = ""
    private val updateSubject = BehaviorSubject.createDefault(code)

    override fun onUiUpdateRequest(): Observable<String> = updateSubject

    override fun sendCode(): Observable<AuthResponseState> = repository.sendCode(code.toInt())

    override fun onPassCodeChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            code += newNumber
        } else {
            if (code.isNotEmpty()) {
                code = code.dropLast(1)
            }
        }

        updateUi()

        if (code.length >= codeLength) {
            sendCode()
        }
    }

    override fun clearCode() {
        code = ""
        updateUi()
    }

    override fun removeLastCodeSymbol() {
        if (code.isNotEmpty()) {
            code = code.dropLast(1)
            updateUi()
        }
    }

    private fun updateUi() {
        updateSubject.onNext(code)
    }

}