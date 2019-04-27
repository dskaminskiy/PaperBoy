package deniskaminskiy.paperboy.data.api.interceptors

import android.os.Build
import deniskaminskiy.paperboy.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class ApplicationVersionInterceptor : Interceptor {

    companion object {
        private const val PROPERTY_OS = "os"
        private const val PROPERTY_VERSION = "version"
        private const val PROPERTY_OS_VERSION = "osVersion"
        private const val PROPERTY_DEVICE = "device"

        private const val POST = "POST"
        private const val DELETE = "DELETE"
        private const val GET = "GET"

        private const val PARAM_OS = "android"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val method = request.method()

        return chain.proceed(
            if (method.equals(POST, true) || method.equals(DELETE, true)) {
                addAppVersionInPostOrDeleteRequest(request)
            } else {
                addAppVersionInGetRequest(request)
            }
        )
    }

    private fun addAppVersionInPostOrDeleteRequest(originalRequest: Request): Request {

    }

    private fun addAppVersionInGetRequest(originalRequest: Request): Request {

    }

    private fun generateAppVersionJsonObject() = JSONObject().apply {
        put(PROPERTY_OS, PARAM_OS)
        put(PROPERTY_VERSION, BuildConfig.VERSION_NAME)
        put(PROPERTY_OS_VERSION, Build.VERSION.RELEASE)
        put(PROPERTY_DEVICE, Build.MODEL)
    }

}