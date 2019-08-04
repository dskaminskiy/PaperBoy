package deniskaminskiy.paperboy.presentation.main

import deniskaminskiy.paperboy.core.BasePresenterImpl

class MainPresenter(
    view: MainView
) : BasePresenterImpl<MainView>(view) {

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)
        if (isViewCreated) {
            view?.showAuth()
        }
    }

}