package deniskaminskiy.paperboy.data.api

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    val paperboyApi: PaperboyApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://5.101.180.46/api/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.newThread()))
            .build()
            .create(PaperboyApi::class.java)
    }

}