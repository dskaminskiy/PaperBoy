package deniskaminskiy.paperboy.data.auth.sources

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.PaperboyApi
import deniskaminskiy.paperboy.data.api.json.auth.*
import deniskaminskiy.paperboy.data.auth.Auth
import deniskaminskiy.paperboy.data.auth.sources.mappers.AuthCodeResponseToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.auth.sources.mappers.AuthPasswordResponseToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.auth.sources.mappers.AuthResponseJsonToAuthMapper
import io.reactivex.Observable

interface AuthCloudDataSource {

    fun requestCode(phoneNumber: String): Observable<Auth>

    fun sendCode(code: String): Observable<AuthResponseState>

    fun sendSecurityCode(code: String, token: String): Observable<AuthResponseState>

}

class AuthCloudDataSourceImpl(
    private val api: PaperboyApi = ApiService.paperboyApi,
    private val authMapper: Mapper<AuthResponseJson, Auth> = AuthResponseJsonToAuthMapper(),
    private val authCodeMapper: Mapper<AuthCodeResponseJson, AuthResponseState> =
        AuthCodeResponseToAuthResponseStateMapper(),
    private val authPasswordMapper: Mapper<AuthPasswordResponseJson, AuthResponseState> =
        AuthPasswordResponseToAuthResponseStateMapper()
) : AuthCloudDataSource {

    override fun requestCode(phoneNumber: String): Observable<Auth> =
        api.auth(phoneNumber)
            .map{ it.soleData?.let(authMapper::map) ?: Auth.EMPTY }
            .toObservable()

    override fun sendCode(code: String): Observable<AuthResponseState> =
        api.authCode(AuthCodeRequestJson(code))
            .map{ it.soleData?.let(authCodeMapper::map) ?: AuthResponseState.ERROR }
            .toObservable()

    override fun sendSecurityCode(code: String, token: String): Observable<AuthResponseState> =
        api.authPassword(AuthPasswordRequestJson(token, code))
            .map{ it.soleData?.let(authPasswordMapper::map) ?: AuthResponseState.ERROR }
            .toObservable()

}