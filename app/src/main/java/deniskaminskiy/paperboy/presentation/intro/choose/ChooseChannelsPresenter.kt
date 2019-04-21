package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.channel.ChannelImport
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.paintWord
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
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val disposableComposite: CompositeDisposable = CompositeDisposable(),
    private val mapperToPresentModel: Mapper<List<ChannelImport>, List<CheckItemPresentItemModel<ChannelImport>>> =
        ChannelImportToPresentModelListMapper()
) : BasePresenterImpl<ChooseChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.chooseChannelsYouWantImportSentence
            .paintWord(resources.chooseChannelsYouWantImportAccentWord ,colors.marlboroNew)
    }

    private val subtitle: String
        get() = resources.youHaveChannels(interactor.channelsCount())

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        disposableComposite.add(
            interactor.channelsImport()
                .compose(composer.observable())
                .subscribe(::onChannelsImportUpdate) {
                    view?.showUnknownError()
                }
        )

    }

    private fun onChannelsImportUpdate(channels: List<ChannelImport>) {
        view?.show(
            ChooseChannelsPresentModel(
                title = title,
                subtitle = subtitle,
                channels = mapperToPresentModel.map(channels),
                isFabVisible = channels.any { it.isChecked }
            )
        )
    }

    fun onItemClick(model: CheckItemPresentItemModel<ChannelImport>) {
        interactor.changeCheckStatus(model.element)
    }

    override fun onViewDetached() {
        disposableComposite.dispose()
        super.onViewDetached()
    }

    fun onSkipClick() {
        // temp
        view?.showRemoveTelegramChannels()
    }

    fun onFabClick() {
        view?.showRemoveTelegramChannels()
    }

}

class ChannelImportToPresentModelListMapper :
    Mapper<List<ChannelImport>, List<CheckItemPresentItemModel<ChannelImport>>> {
    override fun map(from: List<ChannelImport>): List<CheckItemPresentItemModel<ChannelImport>> =
        from.map {
            CheckItemPresentItemModel(
                element = it,
                model = CheckItemPresentModel(
                    title = it.title,
                    isDivider = from.last() != it,
                    isChecked = it.isChecked
                )
            )
        }
}
