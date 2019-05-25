package deniskaminskiy.paperboy.data.settings

import android.content.Context
import deniskaminskiy.paperboy.utils.ContextDelegate

interface PreferenceHelper {

    var userToken: String

}

class PreferenceHelperImpl(
    private val contextDelegate: ContextDelegate
) : PreferenceHelper {

    companion object {
        private const val PREF_KEY_USER = "deniskaminskiy.paperboy.data.user"

        private const val KEY_USER_TOKEN = "USER_TOKEN"
    }

    override var userToken: String
        set(value) {
            contextDelegate.getContext()?.getSharedPreferences(PREF_KEY_USER, Context.MODE_PRIVATE)?.edit()
                ?.let { prefs ->
                    prefs.putString(KEY_USER_TOKEN, value)
                    prefs.apply()
                }
        }
        get() = contextDelegate.getContext()?.getSharedPreferences(PREF_KEY_USER, Context.MODE_PRIVATE)
            ?.getString(KEY_USER_TOKEN, "")
            ?: ""

}