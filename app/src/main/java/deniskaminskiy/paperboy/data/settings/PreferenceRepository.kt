package deniskaminskiy.paperboy.data.settings

import android.content.Context
import deniskaminskiy.paperboy.utils.ContextDelegate

interface PreferenceRepository {

    var userToken: String

}

class PreferenceRepositoryImpl(
    private val contextDelegate: ContextDelegate
) : PreferenceRepository {

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