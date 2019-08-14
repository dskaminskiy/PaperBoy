package deniskaminskiy.paperboy.presentation.intro.remove

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.paintWord

class RemoveTelegramChannelsPresenter(
    view: RemoveTelegramChannelsView,
    private val resources: ResourcesProvider
) : BasePresenterImpl<RemoveTelegramChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.provideString(R.string.now_you_can_remove_channels_from_telegram_sentence)
            .paintWord(
                resources.provideString(R.string.now_you_can_remove_channels_from_telegram_accent_word),
                resources.provideColor(R.color.marlboroNew)
            )
    }

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)
        view?.show(RemoveTelegramChannelsPresentModel(title))
    }

    fun onMaybeLayterClick() {
        view?.showHome()
    }

}