package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.TextUtils
import deniskaminskiy.paperboy.utils.managers.ResourcesManager

class ChooseChannelsPresenter(
    view: ChooseChannelsView,
    isChannelsFetched: Boolean,
    private val resources: ResourcesManager,
    private val colors: Colors,
    private val interactor: ChooseChannelsInteractor =
        ChooseChannelsInteractorImpl(isChannelsFetched)
) : BasePresenterImpl<ChooseChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.chooseChannelsYouWantImport.let { sentence ->
            val lastWord = sentence.substring(sentence.lastIndexOf(" ") + 1)
            val otherSentence = sentence.dropLast(lastWord.length)

            SpannableStringBuilder().append(
                TextUtils.paintString(otherSentence, colors.print100),
                TextUtils.paintString(lastWord, colors.marlboroNew))
        }
    }

    private val subtitle: String by lazy {
        resources.youHaveChannels(interactor.channelsCount())
    }

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)


    }

    fun onSkipClick() {

    }

}