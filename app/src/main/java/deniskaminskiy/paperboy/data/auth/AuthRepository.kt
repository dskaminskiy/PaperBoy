package deniskaminskiy.paperboy.data.auth

import deniskaminskiy.paperboy.core.Factory
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.sources.AuthCloudDataSource
import deniskaminskiy.paperboy.data.auth.sources.AuthCloudDataSourceImpl
import io.reactivex.Observable

interface AuthRepository {

    fun requestCode(phoneNumber: String): Observable<Auth>

    fun sendCode(code: Int): Observable<AuthResponseState>

    fun sendSecurityCode(code: String, token: String): Observable<AuthResponseState>

}

class AuthRepositoryImpl(
    private val cloud: AuthCloudDataSource = AuthCloudDataSourceImpl()
) : AuthRepository {

    override fun requestCode(phoneNumber: String): Observable<Auth> =
            cloud.requestCode(phoneNumber)

    override fun sendCode(code: Int): Observable<AuthResponseState> =
            cloud.sendCode(code)

    override fun sendSecurityCode(code: String, token: String): Observable<AuthResponseState> =
            cloud.sendSecurityCode(code, token)

}

object AuthRepositoryFactory : Factory<AuthRepository> {

    private val instance by lazy { AuthRepositoryImpl() }

    override fun create(): AuthRepository = instance

}