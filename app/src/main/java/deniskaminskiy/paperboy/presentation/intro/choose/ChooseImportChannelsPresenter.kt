package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.api.fold
import deniskaminskiy.paperboy.utils.api.responseOrError
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.paintWord
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.CompositeDisposable

class ChooseImportChannelsPresenter(
    view: ChooseImportChannelsView,
    isChannelsFetched: Boolean,
    private val resources: ResourcesManager,
    private val interactor: ChooseImportChannelsInteractor =
        ChooseImportChannelsInteractorImpl(isChannelsFetched),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val disposableComposite: CompositeDisposable = CompositeDisposable(),
    private val mapperToPresentModel: Mapper<List<ImportChannel>, List<CheckItemPresentItemModel<ImportChannel>>> =
        ChannelImportToPresentModelListMapper()
) : BasePresenterImpl<ChooseImportChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.strings.chooseChannelsYouWantImportSentence
            .paintWord(resources.strings.chooseChannelsYouWantImportAccentWord, resources.colors.marlboroNew)
    }

    private val subtitle: String
        get() = resources.strings.youHaveChannels(interactor.channelsCount())

    private val unknownError: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources.strings.somethingHappened,
            subtitle = resources.strings.sometimesShitHappens,
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources.colors.marlboroNew
        )
    }

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        disposableComposite.add(
            interactor.channels()
                // тут могло бы быть .doOnSubscribe{ view?.showLoading() }, но loading-state тут не будет
                .compose(composer.observable())
                .subscribe(::onChannelsImportUpdate) { t ->
                    t.responseOrError()
                        .fold({
                            view?.showTopPopup(unknownError.copy(subtitle = it.message))
                        }, {
                            view?.showTopPopup(unknownError)
                        })
                }
        )

    }

    private fun onChannelsImportUpdate(channels: List<ImportChannel>) {
        view?.show(
            ChooseImportChannelsPresentModel(
                title = title,
                subtitle = subtitle,
                channels = mapperToPresentModel.map(channels),
                isFabVisible = channels.any { it.isChecked }
            )
        )
    }

    fun onItemClick(model: CheckItemPresentItemModel<ImportChannel>) {
        interactor.changeCheckStatus(model.element)
    }

    override fun onDestroy() {
        disposableComposite.dispose()
        super.onDestroy()
    }

    fun onSkipClick() {
        view?.showRemoveTelegramChannels()
    }

    fun onFabClick() {
        view?.showRemoveTelegramChannels()
    }

}

class ChannelImportToPresentModelListMapper :
    Mapper<List<ImportChannel>, List<CheckItemPresentItemModel<ImportChannel>>> {
    override fun map(from: List<ImportChannel>): List<CheckItemPresentItemModel<ImportChannel>> =
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
