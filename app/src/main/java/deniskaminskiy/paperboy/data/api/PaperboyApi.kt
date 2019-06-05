package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.data.api.json.DataResponseJson
import deniskaminskiy.paperboy.data.api.json.auth.*
import deniskaminskiy.paperboy.data.api.json.importchannels.ImportChannelResponseJson
import io.reactivex.Completable
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
    fun auth(@Query("phoneNumber") phoneNumber: String): Single<DataResponseJson<AuthResponseJson>>

    @POST("auth/code")
    fun authCode(@Body request: AuthCodeRequestJson): Single<DataResponseJson<AuthCodeResponseJson>>

    @POST("auth/password")
    fun authPassword(@Body request: AuthPasswordRequestJson): Single<DataResponseJson<AuthPasswordResponseJson>>

    @GET("me/channels")
    fun importChannels(): Single<DataResponseJson<ImportChannelResponseJson>>

    @POST("me/channels/subscriptions")
    fun subscribeChannels(channelsIds: List<Long>): Completable

}