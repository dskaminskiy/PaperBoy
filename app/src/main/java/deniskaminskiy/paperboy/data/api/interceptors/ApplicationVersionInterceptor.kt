package deniskaminskiy.paperboy.data.api.interceptors

import android.os.Build
import deniskaminskiy.paperboy.BuildConfig
import deniskaminskiy.paperboy.utils.InterceptorsUtils
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject

class ApplicationVersionInterceptor : Interceptor {

    companion object {
        private const val PROPERTY_OS = "os"
        private const val PROPERTY_VERSION = "version"
        private const val PROPERTY_OS_VERSION = "osVersion"
        private const val PROPERTY_DEVICE = "device"

        private const val PARAM_OS = "android"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader(PROPERTY_OS, PARAM_OS)
            .addHeader(PROPERTY_VERSION, BuildConfig.VERSION_NAME)
            .addHeader(PROPERTY_OS_VERSION, Build.VERSION.RELEASE)
            .addHeader(PROPERTY_DEVICE, Build.MODEL)
            .build()
    )

}