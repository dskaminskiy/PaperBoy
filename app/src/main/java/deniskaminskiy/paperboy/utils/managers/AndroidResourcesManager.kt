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

    override val chooseChannelsYouWantImport: String
        get() = getString(R.string.choose_channels_you_want_import)

    override fun youHaveChannels(count: Int): String =
        getContext()?.getString(R.string.template_you_have_channels_you_always_can_import, count) ?: ""

    private fun getString(@StringRes stringId: Int): String =
        getContext()?.getString(stringId) ?: ""

    private fun getContext(): Context? = contextDelegate.getContext()

}