package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.data.api.json.auth.AuthCodeResponse
import deniskaminskiy.paperboy.data.api.json.auth.AuthPasswordRequest
import deniskaminskiy.paperboy.data.api.json.auth.AuthPasswordResponse
import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaperboyApi {

    /**
     * @param phoneNumber     - телефон передается в международном формате (пр: "+79261342532")
     */
    @GET("auth")
    fun auth(@Query("phoneNumber") phoneNumber: String): Single<AuthResponseJson>

    @POST("auth/code")
    fun authCode(@Body code: Int): Single<AuthCodeResponse>

    @POST("auth/password")
    fun authPassword(@Body request: AuthPasswordRequest): Single<AuthPasswordResponse>

}