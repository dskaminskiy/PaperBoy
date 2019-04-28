package deniskaminskiy.paperboy.data.auth.sources

import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.data.api.PaperboyApi
import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import deniskaminskiy.paperboy.data.auth.AuthCode
import io.reactivex.Observable

interface AuthCloudDataSource {

    /**
     * @param phoneNumber     - телефон передается в международном формате (пр: "+79261342532")
     */
    fun requestCode(phoneNumber: String): Observable<AuthCode>

}

class AuthCloudDataSourceImpl(
    private val api: PaperboyApi = ApiService.paperboyApi,
    private val authCodeMapper: Mapper<AuthResponseJson, AuthCode> = AuthResponseJsonToAuthCodeMapper()
) : AuthCloudDataSource {

    override fun requestCode(phoneNumber: String): Observable<AuthCode> =
        api.auth(phoneNumber)
            .map(authCodeMapper::map)
            .toObservable()

}