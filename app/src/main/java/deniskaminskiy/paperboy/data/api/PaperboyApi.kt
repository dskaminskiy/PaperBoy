package deniskaminskiy.paperboy.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface PaperboyApi {

    @GET("/auth")
    fun auth(@Query("phoneNumber") phoneNumber: String)

}