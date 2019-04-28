package deniskaminskiy.paperboy.domain.auth

import deniskaminskiy.paperboy.core.Interactor
import deniskaminskiy.paperboy.data.auth.AuthCode
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import io.reactivex.Observable

interface AuthPhoneInteractor : Interactor {

    fun requestCode(reignNumber: Int, phoneNumber: Long): Observable<AuthCode>

}

class AuthPhoneInteractorImpl(
    private val repository: AuthRepository = AuthRepositoryFactory.create()
) : AuthPhoneInteractor {

    override fun requestCode(reignNumber: Int, phoneNumber: Long): Observable<AuthCode> = repository
        .requestCode("+$reignNumber$phoneNumber")

}