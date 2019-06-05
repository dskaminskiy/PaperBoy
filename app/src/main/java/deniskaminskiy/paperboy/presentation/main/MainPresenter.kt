package deniskaminskiy.paperboy.presentation.main

import deniskaminskiy.paperboy.core.BasePresenterImpl

class MainPresenter(
    view: MainView
) : BasePresenterImpl<MainView>(view) {

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        if (viewCreated) {
            view?.showAuth()
        }
    }

}