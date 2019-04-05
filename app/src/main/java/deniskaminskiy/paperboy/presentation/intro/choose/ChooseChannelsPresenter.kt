package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.channel.ChannelImport
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.TextUtils
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.CompositeDisposable

class ChooseChannelsPresenter(
    view: ChooseChannelsView,
    isChannelsFetched: Boolean,
    private val resources: ResourcesManager,
    private val colors: Colors,
    private val interactor: ChooseChannelsInteractor =
        ChooseChannelsInteractorImpl(isChannelsFetched),
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<ChooseChannelsView>(view) {

    private val compositeDisposable = CompositeDisposable()

    private val title: SpannableStringBuilder by lazy {
        resources.chooseChannelsYouWantImport.let { sentence ->
            val lastWord = sentence.substring(sentence.lastIndexOf(" ") + 1)
            val otherSentence = sentence.dropLast(lastWord.length)

            SpannableStringBuilder().append(
                TextUtils.paintString(otherSentence, colors.print100),
                TextUtils.paintString(lastWord, colors.marlboroNew)
            )
        }
    }

    private val subtitle: String by lazy {
        resources.youHaveChannels(interactor.channelsCount())
    }

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        compositeDisposable.add(
            interactor.channelsImport()
                .compose(composer.observable())
                .subscribe({
                    //TODO: map and show
                }, {
                    view?.showUnknownError()
                })
        )

    }

    override fun onViewDetached() {
        compositeDisposable.dispose()
        super.onViewDetached()
    }

    fun onSkipClick() {

    }

}

class ChannelImportToPresentModelListMapper : Mapper<List<ChannelImport>, List<CheckItemPresentItemModel<ChannelImport>>> {
    override fun map(from: List<ChannelImport>): List<CheckItemPresentItemModel<ChannelImport>> =
        from.map {
            CheckItemPresentItemModel(
                element = it,
                model = CheckItemPresentModel(
                    title = it.title,
                    isDivider = from.last() != it
                )
            )
        }
}
