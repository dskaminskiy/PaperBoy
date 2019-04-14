package deniskaminskiy.paperboy.presentation.intro.remove

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.paintWord

class RemoveTelegramChannelsPresenter(
    view: RemoveTelegramChannelsView,
    private val resources: ResourcesManager,
    private val colors: Colors
) : BasePresenterImpl<RemoveTelegramChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.nowYouCanRemoveChannelsFromTelegramSentence
            .paintWord(resources.nowYouCanRemoveChannelsFromTelegramAccentWord, colors.marlboroNew)
    }

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        view?.show(RemoveTelegramChannelsPresentModel(title))
    }

}