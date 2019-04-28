package deniskaminskiy.paperboy.data.api

import deniskaminskiy.paperboy.data.api.interceptors.utils.StringEscapeUtils
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.Platform.INFO
import okhttp3.logging.HttpLoggingInterceptor

class PaperboyLogger: HttpLoggingInterceptor.Logger {

    override fun log(message: String) =
        Platform.get().log(INFO, StringEscapeUtils.unescapeJava(message), null)

}