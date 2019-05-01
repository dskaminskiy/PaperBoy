package deniskaminskiy.paperboy.data.auth.sources

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.api.PaperboyApi
import deniskaminskiy.paperboy.data.api.json.auth.AuthCodeResponse
import deniskaminskiy.paperboy.data.api.json.auth.AuthPasswordRequest
import deniskaminskiy.paperboy.data.api.json.auth.AuthPasswordResponse
import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import deniskaminskiy.paperboy.data.auth.Auth
import deniskaminskiy.paperboy.data.auth.sources.mappers.AuthCodeResponseToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.auth.sources.mappers.AuthPasswordResponseToAuthResponseStateMapper
import deniskaminskiy.paperboy.data.auth.sources.mappers.AuthResponseJsonToAuthMapper
import io.reactivex.Observable

interface AuthCloudDataSource {

    fun requestCode(phoneNumber: String): Observable<Auth>

    fun sendCode(code: Int): Observable<AuthResponseState>

    fun sendSecurityCode(code: String, token: String): Observable<AuthResponseState>

}

class AuthCloudDataSourceImpl(
    private val api: PaperboyApi = ApiService.paperboyApi,
    private val authMapper: Mapper<AuthResponseJson, Auth> = AuthResponseJsonToAuthMapper(),
    private val authCodeMapper: Mapper<AuthCodeResponse, AuthResponseState> =
        AuthCodeResponseToAuthResponseStateMapper(),
    private val authPasswordMapper: Mapper<AuthPasswordResponse, AuthResponseState> =
        AuthPasswordResponseToAuthResponseStateMapper()
) : AuthCloudDataSource {

    override fun requestCode(phoneNumber: String): Observable<Auth> =
        api.auth(phoneNumber)
            .map(authMapper::map)
            .toObservable()

    override fun sendCode(code: Int): Observable<AuthResponseState> =
        api.authCode(code)
            .map(authCodeMapper::map)
            .toObservable()

    override fun sendSecurityCode(code: String, token: String): Observable<AuthResponseState> =
        api.authPassword(AuthPasswordRequest(token, code))
            .map(authPasswordMapper::map)
            .toObservable()

}