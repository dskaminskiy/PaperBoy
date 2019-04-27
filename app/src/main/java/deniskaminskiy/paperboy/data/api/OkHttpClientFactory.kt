package deniskaminskiy.paperboy.data.api

import com.readystatesoftware.chuck.ChuckInterceptor
import deniskaminskiy.paperboy.core.Factory
import deniskaminskiy.paperboy.utils.ContextDelegate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpClientFactory(
    private val contextDelegate: ContextDelegate,
    private val okHttpBuilderFactory: Factory<OkHttpClient.Builder>,
    private val logger: HttpLoggingInterceptor.Logger
) : Factory<OkHttpClient> {

    override fun create(): OkHttpClient {
        val builder = okHttpBuilderFactory.create()
            .addInterceptor(ChuckInterceptor(contextDelegate.getContext()))

        return builder.build()
    }

}