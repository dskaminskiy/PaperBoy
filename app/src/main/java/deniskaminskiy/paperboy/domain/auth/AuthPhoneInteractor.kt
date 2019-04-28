package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.auth.Auth
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import io.reactivex.Observable

interface AuthPhoneInteractor : Interactor {

    fun requestCode(reignNumber: Int, phoneNumber: Long): Observable<Auth>

}

class AuthPhoneInteractorImpl(
    private val repository: AuthRepository = AuthRepositoryFactory.create()
) : AuthPhoneInteractor {

    override fun requestCode(reignNumber: Int, phoneNumber: Long): Observable<Auth> = repository
        .requestCode("+$reignNumber$phoneNumber")

}