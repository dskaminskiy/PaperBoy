package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.utils.ContextDelegate
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "http://5.101.180.46:8080/api/v1/"

    var paperboyApi: PaperboyApi = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .build()
        .create(PaperboyApi::class.java)

    fun init(contextDelegate: ContextDelegate) {
        paperboyApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.newThread())
            )
            .client(OkHttpClientFactory(contextDelegate, OkHttpBuilderFactory(), PaperboyLogger()).create())
            .build()
            .create(PaperboyApi::class.java)
    }

}