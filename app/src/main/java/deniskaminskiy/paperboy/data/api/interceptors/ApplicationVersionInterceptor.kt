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

        private const val PROPERTY_APP_OS = "application[os]"
        private const val PROPERTY_APP_VERSION = "application[version]"
        private const val PROPERTY_APP_OS_VERSION = "application[osVersion]"
        private const val PROPERTY_APP_DEVICE = "application[device]"

        private const val PROPERTY_APPLICATION = "application"

        private const val POST = "POST"
        private const val DELETE = "DELETE"

        private const val PARAM_OS = "android"

        private const val DEFAULT_MEDIA_TYPE = "application/json; charset=utf-8"
    }

    private val defaultMediaType by lazy { MediaType.parse(DEFAULT_MEDIA_TYPE) }

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
        val builder = originalRequest.newBuilder()
        val bodyStr = InterceptorsUtils.bodyToString(originalRequest.body())

        var jsonBody = JSONObject()

        try {
            if (!bodyStr.isEmpty()) jsonBody = JSONObject(bodyStr)

            jsonBody.put(PROPERTY_APPLICATION, generateAppVersionJsonObject())

            if (originalRequest.method().equals(POST, true)) {
                return builder
                    .post(RequestBody.create(defaultMediaType, jsonBody.toString()))
                    .build()
            } else if (originalRequest.method().equals(DELETE, true)) {
                return builder
                    .delete(RequestBody.create(defaultMediaType, jsonBody.toString()))
                    .build()
            }
        } catch (e: JSONException) {
            //..
        }

        return builder.build()
    }

    private fun generateAppVersionJsonObject() = JSONObject().apply {
        put(PROPERTY_OS, PARAM_OS)
        put(PROPERTY_VERSION, BuildConfig.VERSION_NAME)
        put(PROPERTY_OS_VERSION, Build.VERSION.RELEASE)
        put(PROPERTY_DEVICE, Build.MODEL)
    }

    private fun addAppVersionInGetRequest(originalRequest: Request): Request =
        originalRequest.newBuilder()
            .url(
                originalRequest.url().newBuilder()
                    .setQueryParameter(PROPERTY_APP_OS, PARAM_OS)
                    .setQueryParameter(PROPERTY_APP_VERSION, BuildConfig.VERSION_NAME)
                    .setQueryParameter(PROPERTY_APP_OS_VERSION, Build.VERSION.RELEASE)
                    .setQueryParameter(PROPERTY_APP_DEVICE, Build.MODEL)
                    .build()
            ).build()

}