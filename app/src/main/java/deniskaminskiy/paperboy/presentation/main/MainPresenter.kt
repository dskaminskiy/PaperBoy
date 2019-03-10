package deniskaminskiy.paperboy.presentation.main

import deniskaminskiy.paperboy.core.BasePresenterImpl
import ru.terrakok.cicerone.Router

class MainPresenter(
    view: MainView,
    private val router: Router
) : BasePresenterImpl<MainView>(view) {

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
    }

}