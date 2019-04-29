package deniskaminskiy.paperboy.data.api.interceptors

import deniskaminskiy.paperboy.data.settings.ApplicationSettingsImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.InterceptorsUtils
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject

class UserTokenInterceptor(
    private val contextDelegate: ContextDelegate
) : Interceptor {

    companion object {
        private const val PROPERTY_TOKEN = "token"

        private const val GET = "GET"
        private const val POST = "POST"
        private const val DELETE = "DELETE"

        private const val MEDIA_TYPE = "application/json; charset=utf-8"
    }

    private val userToken: String
        get() = ApplicationSettingsImpl(contextDelegate).userToken

    private val mediaType: MediaType?
        get() = MediaType.parse(MEDIA_TYPE)

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        if (originalRequest.method().equals(GET, true)) {
            builder.url(
                originalRequest.url().newBuilder()
                    .setQueryParameter(PROPERTY_TOKEN, userToken)
                    .build()
            )
        } else {
            val bodyString = InterceptorsUtils.bodyToString(originalRequest.body())
            val jsonBody = if (bodyString.isBlank()) JSONObject() else JSONObject(bodyString)

            jsonBody.put(PROPERTY_TOKEN, userToken)

            if (originalRequest.method().equals(POST, true)) {
                builder.post(RequestBody.create(mediaType, jsonBody.toString()))
            } else if (originalRequest.method().equals(DELETE, true)) {
                builder.delete(RequestBody.create(mediaType, jsonBody.toString()))
            }
        }

        return chain.proceed(builder.build())
    }

}