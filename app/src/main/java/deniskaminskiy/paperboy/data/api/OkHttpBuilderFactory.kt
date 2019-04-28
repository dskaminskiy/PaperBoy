package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.core.Factory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpBuilderFactory : Factory<OkHttpClient.Builder> {

    companion object {
        private const val TIMEOUT_READ_MINUTES: Long = 1
        private const val TIMEOUT_CONNECT_MINUTES: Long = 1
    }

    override fun create(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_READ_MINUTES, TimeUnit.MINUTES)
            .connectTimeout(TIMEOUT_CONNECT_MINUTES, TimeUnit.MINUTES)
    }

}