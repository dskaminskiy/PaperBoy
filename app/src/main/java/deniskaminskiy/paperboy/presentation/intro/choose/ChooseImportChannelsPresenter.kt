package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.domain.intro.ChooseImportChannelsInteractor
import deniskaminskiy.paperboy.domain.intro.ChooseImportChannelsInteractorImpl
import deniskaminskiy.paperboy.presentation.base.CheckItemPresentItemModel
import deniskaminskiy.paperboy.presentation.base.SuperItemPresentItemModel
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.paintWord
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull
import io.reactivex.disposables.Disposable

class ChooseImportChannelsPresenter(
    view: ChooseImportChannelsView,
    private val resources: ResourcesProvider,
    private val interactor: ChooseImportChannelsInteractor =
        ChooseImportChannelsInteractorImpl(),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val mapperToPresentModel: Mapper<List<ImportChannel>, List<CheckItemPresentItemModel<ImportChannel>>> =
        ChannelImportToPresentModelListMapper()
) : BasePresenterImpl<ChooseImportChannelsView>(view) {

    private val title: SpannableStringBuilder by lazy {
        resources.provideString(R.string.choose_channels_you_want_import_sentence)
            .paintWord(
                resources.provideString(R.string.choose_channels_you_want_import_accent_word),
                resources.provideColor(R.color.marlboroNew)
            )
    }

    private val subtitle: String
        get() = resources.provideString(
            R.string.template_you_have_channels_you_always_can_import,
            interactor.channelsCount()
        )

    private var disposableSubscribeChannels: Disposable? = null

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)

        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.channels(isViewCreated)
            .compose(composer.observable())
            .subscribe(
                ::onChannelsImportUpdate,
                ::defaultOnError
            )
    }

    override fun onViewDetached() {
        disposableSubscribeChannels.disposeIfNotNull()
        view?.hideFab()
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
        disposableSubscribeChannels.disposeIfNotNull()
        disposableSubscribeChannels = interactor.subscribeChannels()
            .compose(composer.completable())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribe({
                view?.showRemoveTelegramChannels()
            }, ::defaultOnError)
    }

    fun onItemClick(item: SuperItemPresentItemModel) {
        item.ifTypeOf(interactor::changeCheckStatus)
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
