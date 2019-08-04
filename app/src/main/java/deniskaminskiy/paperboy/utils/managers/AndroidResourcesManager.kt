package deniskaminskiy.paperboy.utils.managers

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.utils.*

class AndroidResourcesManager private constructor(
    private val contextDelegate: ContextDelegate
) : ResourcesManager {

    override val colors: ResourcesManager.Colors by lazy { Colors() }
    override val strings: ResourcesManager.Strings by lazy { Strings() }
    override val fonts: ResourcesManager.Fonts by lazy { Fonts() }

    companion object {

        fun create(fragment: Fragment) = AndroidResourcesManager(FragmentContextDelegate(fragment))

        fun create(contextDelegate: ContextDelegate) = AndroidResourcesManager(contextDelegate)

        fun create(context: Context) = AndroidResourcesManager(ContextContextDelegate(context))

    }

    constructor(context: Context) : this(ContextContextDelegate(context))

    private inner class Colors : ResourcesManager.Colors {

        override val primary: Int
            get() = c(R.color.colorPrimary)
        override val primaryDark: Int
            get() = c(R.color.colorPrimaryDark)
        override val accent: Int
            get() = c(R.color.colorAccent)

        override val paper: Int
            get() = c(R.color.paper)

        override val print10: Int
            get() = c(R.color.print10)
        override val print15: Int
            get() = c(R.color.print15)
        override val print20: Int
            get() = c(R.color.print20)
        override val print30: Int
            get() = c(R.color.print30)
        override val print40: Int
            get() = c(R.color.print40)
        override val print50: Int
            get() = c(R.color.print50)
        override val print60: Int
            get() = c(R.color.print60)
        override val print70: Int
            get() = c(R.color.print70)
        override val print80: Int
            get() = c(R.color.print80)
        override val print90: Int
            get() = c(R.color.print90)
        override val print100: Int
            get() = c(R.color.print100)

        override val marlboroNew: Int
            get() = c(R.color.marlboroNew)
        override val marlboroOld: Int
            get() = c(R.color.marlboroOld)

        override val admiral: Int
            get() = c(R.color.admiral)

        private fun c(@ColorRes resId: Int): Int =
            contextDelegate.getContext().compatColor(resId)
    }

    private inner class Strings : ResourcesManager.Strings {

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

        override val noNetworkConnection: String
            get() = getString(R.string.no_network_connection)

        override val pleaseFixItAndTryAgain: String
            get() = getString(R.string.please_fix_it_and_try_again)


        override val somethingHappened: String
            get() = getString(R.string.oops_something_happened)

        override val sometimesShitHappens: String
            get() = getString(R.string.it_was_shit_sometimes_shit_happens)


        private fun getString(@StringRes stringId: Int): String =
            contextDelegate.getContext()?.getString(stringId) ?: ""

        private fun getContext(): Context? = contextDelegate.getContext()

    }

    private inner class Fonts : ResourcesManager.Fonts {

        override val sansMedium: Typeface
            get() = getFont(R.font.ibm_plex_sans_medium)

        override val sansSemibold: Typeface
            get() = getFont(R.font.ibm_plex_sans_semibold)

        override val sansBold: Typeface
            get() = getFont(R.font.ibm_plex_sans_bold)

        override val sansText: Typeface
            get() = getFont(R.font.ibm_plex_sans_text)

        override val serifBold: Typeface
            get() = getFont(R.font.ibm_plex_serif_bold)

        override val serifSemibold: Typeface
            get() = getFont(R.font.ibm_plex_serif_semibold)

        private fun getFont(@FontRes fontId: Int): Typeface =
            contextDelegate.getContext().compatFont(fontId)

    }

}