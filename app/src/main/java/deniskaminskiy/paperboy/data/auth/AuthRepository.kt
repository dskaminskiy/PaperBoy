package deniskaminskiy.paperboy.data.auth

import deniskaminskiy.paperboy.core.Factory
import deniskaminskiy.paperboy.data.auth.sources.AuthCloudDataSource
import deniskaminskiy.paperboy.data.auth.sources.AuthCloudDataSourceImpl
import io.reactivex.Observable

interface AuthRepository {

    fun requestCode(phoneNumber: String): Observable<AuthCode>

}

class AuthRepositoryImpl(
    private val cloud: AuthCloudDataSource = AuthCloudDataSourceImpl()
) : AuthRepository {

    override fun requestCode(phoneNumber: String): Observable<AuthCode> =
            cloud.requestCode(phoneNumber)

}

object AuthRepositoryFactory : Factory<AuthRepository> {

    private val instance by lazy { AuthRepositoryImpl() }

    override fun create(): AuthRepository = instance

}