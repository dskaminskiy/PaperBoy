package deniskaminskiy.paperboy.presentation.home

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.utils.managers.ResourcesManager

class HomePresenter(
    view: HomeView,
    private val resources: ResourcesManager
) : BasePresenterImpl<HomeView>(view) {

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

        if (viewCreated) {
            view?.showTitleTypeface(resources.fonts.serifBold)
        }
    }

}