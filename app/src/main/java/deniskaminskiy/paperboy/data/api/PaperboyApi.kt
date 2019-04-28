package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.data.api.json.auth.AuthResponseJson
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PaperboyApi {

    @GET("auth")
    fun auth(@Query("phoneNumber") phoneNumber: String) : Single<AuthResponseJson>

}