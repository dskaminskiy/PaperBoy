package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.domain.intro.ChooseImportChannelsInteractor
import deniskaminskiy.paperboy.domain.intro.ChooseImportChannelsInteractorImpl
import deniskaminskiy.paperboy.presentation.base.CheckItemPresentItemModel
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.paintWord
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull
import io.reactivex.disposables.Disposable

class ChooseImportChannelsPresenter(
    view: ChooseImportChannelsView,
    private val resources: ResourcesManager,
    private val interactor: ChooseImportChannelsInteractor =
        ChooseImportChannelsInteractorImpl(),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val mapperToPresentModel: Mapper<List<ImportChannel>, List<CheckItemPresentItemModel<ImportChannel>>> =
        ChannelImportToPresentModelListMapper()
) : BasePresenterImpl<ChooseImportChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.strings.chooseChannelsYouWantImportSentence
            .paintWord(resources.strings.chooseChannelsYouWantImportAccentWord, resources.colors.marlboroNew)
    }

    private val subtitle: String
        get() = resources.strings.youHaveChannels(interactor.channelsCount())

    private var disposableSubscribeChannels: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        disposableUpdateUi = interactor.channels()
            // тут могло бы быть .doOnSubscribe{ view?.showLoading() }, но loading-state тут не будет
            .compose(composer.observable())
            .subscribe(::onChannelsImportUpdate, ::onError)
    }

    override fun onViewDetached() {
        disposableSubscribeChannels.disposeIfNotNull()
        super.onViewDetached()
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

    private fun subscribeChannels() {
        disposableSubscribeChannels = interactor.subscribeChannels()
            .doOnSubscribe { view?.showLoading() }
            .doOnEvent { view?.hideLoading() }
            .compose(composer.completable())
            .subscribe({
                view?.showRemoveTelegramChannels()
            }, ::onError)
    }

    fun onItemClick(item: CheckItemPresentItemModel<ImportChannel>) {
        item.element
    }

    fun onSkipClick() {
        view?.showRemoveTelegramChannels()
    }

    fun onFabClick() {
        subscribeChannels()
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
