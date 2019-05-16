package deniskaminskiy.paperboy.utils.api

import com.google.gson.Gson

object GsonFactory {

    private val gson: Gson by lazy { Gson() }

    fun create(): Gson = gson

}