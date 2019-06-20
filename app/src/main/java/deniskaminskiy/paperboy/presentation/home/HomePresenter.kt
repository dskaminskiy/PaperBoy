package deniskaminskiy.paperboy.presentation.home

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.channels.Channel
import deniskaminskiy.paperboy.domain.home.HomeInteractor
import deniskaminskiy.paperboy.domain.home.HomeInteractorImpl
import deniskaminskiy.paperboy.presentation.base.DividerPresentItemModel
import deniskaminskiy.paperboy.presentation.base.MiddleItemPresentItemModel
import deniskaminskiy.paperboy.presentation.base.MiddleItemToSuperItemPresentItemModelMapper
import deniskaminskiy.paperboy.presentation.base.SuperItemPresentItemModel
import deniskaminskiy.paperboy.presentation.view.MiddleItemIconConstant
import deniskaminskiy.paperboy.presentation.view.MiddleItemIconUrl
import deniskaminskiy.paperboy.presentation.view.MiddleItemPresentModel
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull

class HomePresenter(
    view: HomeView,
    private val resources: ResourcesManager,
    private val interactor: HomeInteractor = HomeInteractorImpl(),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val superMapper: Mapper<List<Channel>, List<SuperItemPresentItemModel>> =
        MiddleItemToSuperItemPresentItemModelMapper(ChannelToMiddleItemPresentModelMapper())
) : BasePresenterImpl<HomeView>(view) {

    // temp
    private val listHeader = listOf(
        MiddleItemPresentItemModel(Unit,
            MiddleItemPresentModel(
                title = "All unread posts",
                extraTitle = "949",
                icon = MiddleItemIconConstant(
                    icon = IconFactory.create(IconConstant.UNREAD.constant),
                    iconColor = resources.colors.print70
                ),
                isDivider = true)),
        MiddleItemPresentItemModel(Unit,
            MiddleItemPresentModel(
                title = "Bookmarked",
                extraTitle = "4",
                icon = MiddleItemIconConstant(
                    icon = IconFactory.create(IconConstant.BOOKMARK.constant),
                    iconColor = resources.colors.print70
                ),
                isDivider = false)),
        DividerPresentItemModel
    )

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

        if (viewCreated) {
            view?.showTitleTypeface(resources.fonts.serifBold)
        }

        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.channels()
            .map(superMapper::map)
            .map(::removeLastElementDivider)
            .map { listHeader + it }
            .compose(composer.observable())
            .doOnSubscribe { /*view?.showLoading()*/ }
            .subscribe({
                view?.show(it)
            }, ::onError)

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

}

class ChannelToMiddleItemPresentModelMapper : Mapper<Channel, MiddleItemPresentModel> {
    override fun map(from: Channel): MiddleItemPresentModel =
        MiddleItemPresentModel(
            title = "Some title",
            extraTitle = "4",
            isDivider = true,
            icon = MiddleItemIconUrl("https://user-images.githubusercontent.com/24874033/39674914-011fd850-5171-11e8-82b5-01e8613114cf.png")
        )
}