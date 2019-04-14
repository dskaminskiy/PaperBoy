package deniskaminskiy.paperboy.utils.managers

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.ContextContextDelegate
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.FragmentContextDelegate

class AndroidResourcesManager private constructor(
    private val contextDelegate: ContextDelegate
) : ResourcesManager {

    companion object {

        fun create(fragment: Fragment) = AndroidResourcesManager(FragmentContextDelegate(fragment))

    }

    constructor(context: Context) : this(ContextContextDelegate(context))

    override val chooseChannelsYouWantImportSentence: String
        get() = getString(R.string.choose_channels_you_want_import_sentence)

    override val chooseChannelsYouWantImportAccentWord: String
        get() = getString(R.string.choose_channels_you_want_import_accent_word)

    override val nowYouCanRemoveChannelsFromTelegramSentence: String
        get() = getString(R.string.now_you_can_remove_channels_from_telegram_sentence)

    override val nowYouCanRemoveChannelsFromTelegramAccentWord: String
        get() = getString(R.string.now_you_can_remove_channels_from_telegram_accent_word)

    override fun youHaveChannels(count: Int): String =
        getContext()?.getString(R.string.template_you_have_channels_you_always_can_import, count) ?: ""

    private fun getString(@StringRes stringId: Int): String =
        getContext()?.getString(stringId) ?: ""

    private fun getContext(): Context? = contextDelegate.getContext()

}