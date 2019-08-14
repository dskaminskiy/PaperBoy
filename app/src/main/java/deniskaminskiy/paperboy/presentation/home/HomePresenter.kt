package deniskaminskiy.paperboy.presentation.home

import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.channels.Channel
import deniskaminskiy.paperboy.domain.home.HomeInteractor
import deniskaminskiy.paperboy.domain.home.HomeInteractorImpl
import deniskaminskiy.paperboy.presentation.base.DividerPresentItemModel
import deniskaminskiy.paperboy.presentation.base.MiddleItemPresentItemModel
import deniskaminskiy.paperboy.presentation.base.MiddleItemToSuperItemPresentItemModelMapper
import deniskaminskiy.paperboy.presentation.base.SuperItemPresentItemModel
import deniskaminskiy.paperboy.presentation.view.MiddleItemPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemConstantIconPresModel
import deniskaminskiy.paperboy.presentation.view.data.ItemUrlIconPresModel
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesProvider
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull

class HomePresenter(
    view: HomeView,
    private val resources: ResourcesProvider,
    private val interactor: HomeInteractor = HomeInteractorImpl(),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val superMapper: Mapper<List<Channel>, List<SuperItemPresentItemModel>> =
        MiddleItemToSuperItemPresentItemModelMapper(ChannelToMiddleItemPresModelMapper())
) : BasePresenterImpl<HomeView>(view) {

    // temp
    private val listHeader = listOf(
        MiddleItemPresentItemModel(
            Unit,
            MiddleItemPresModel(
                title = "All unread posts",
                extraTitle = "949",
                icon = ItemConstantIconPresModel(
                    icon = IconFactory.create(IconConstant.UNREAD.constant),
                    iconColor = resources.provideColor(R.color.print70)
                ),
                isDivider = true
            )
        ),
        MiddleItemPresentItemModel(
            Unit,
            MiddleItemPresModel(
                title = "Bookmarked",
                extraTitle = "4",
                icon = ItemConstantIconPresModel(
                    icon = IconFactory.create(IconConstant.BOOKMARK.constant),
                    iconColor = resources.provideColor(R.color.print70)
                ),
                isDivider = false
            )
        ),
        DividerPresentItemModel
    )

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)

        if (isViewCreated) {
            view?.showTitleTypeface(resources.provideTypeface(R.font.ibm_plex_serif_bold))
        }

        loadChannels()
    }

    private fun loadChannels() {
        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.channels()
            .map(superMapper::map)
            .map(::removeLastElementDivider)
            .map { listHeader + it }
            .compose(composer.observable())
            .subscribe({
                view?.show(it)
            }, ::defaultOnError)
    }

    private fun removeLastElementDivider(list: List<SuperItemPresentItemModel>) =
        list.map { element ->
            if (element != list.lastOrNull()) {
                element
            } else {
                (element as? MiddleItemPresentItemModel<*>)
                    ?.let { middleItem ->
                        MiddleItemPresentItemModel(middleItem.element, middleItem.model.copy(isDivider = false))
                    }
                    ?: element
            }
        }

    fun onRefresh() {
        view?.disableScrolling()
    }


}

class ChannelToMiddleItemPresModelMapper : Mapper<Channel, MiddleItemPresModel> {
    override fun map(from: Channel): MiddleItemPresModel =
        MiddleItemPresModel(
            title = "Some title",
            extraTitle = "4",
            isDivider = true,
            icon = ItemUrlIconPresModel("https://cdn1.iconfinder.com/data/icons/communications-network-2/96/Newspaper-2-512.png")
        )
}