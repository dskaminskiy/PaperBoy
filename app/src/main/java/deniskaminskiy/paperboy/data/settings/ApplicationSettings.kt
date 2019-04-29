package deniskaminskiy.paperboy.data.settings

import android.content.Context
import deniskaminskiy.paperboy.utils.ContextDelegate

interface ApplicationSettings {

    var userToken: String

}

class ApplicationSettingsImpl(
    private val contextDelegate: ContextDelegate
) : ApplicationSettings {

    companion object {
        private const val SETTINGS_FILE_NAME = "deniskaminskiy.paperboy.data.settings.App"

        private const val KEY_USER_TOKEN = "USER_TOKEN"
    }

    override var userToken: String
        set(value) {
            contextDelegate.getContext()?.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)?.edit()
                ?.let { prefs ->
                    prefs.putString(KEY_USER_TOKEN, value)
                    prefs.apply()
                }
        }
        get() = contextDelegate.getContext()?.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
            ?.getString(KEY_USER_TOKEN, "")
            ?: ""

}