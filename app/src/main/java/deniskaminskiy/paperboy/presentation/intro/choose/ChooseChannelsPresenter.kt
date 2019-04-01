package deniskaminskiy.paperboy.presentation.intro.choose

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.utils.managers.ResourcesManager

class ChooseChannelsPresenter(
    view: ChooseChannelsView,
    private val isChannelsFetched: Boolean,
    private val resources: ResourcesManager,
    private val interactor: ChooseChannelsInteractor = ChooseChannelsInteractorImpl()
) : BasePresenterImpl<ChooseChannelsView>(view) {

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

    }

    fun onSkipClick() {

    }

}