package deniskaminskiy.paperboy.utils

import okhttp3.RequestBody
import okio.Buffer
import java.io.IOException

object InterceptorsUtils {

    fun bodyToString(request: RequestBody?): String =
        request?.let {
            val buffer = Buffer()

            return try {
                val copy = it
                copy.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                ""
            } finally {
                buffer.close()
            }
        } ?: ""


}