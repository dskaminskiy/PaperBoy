package deniskaminskiy.paperboy.presentation.main

import deniskaminskiy.paperboy.core.BasePresenterImpl
import ru.terrakok.cicerone.Router

class MainPresenter(
    private val router: Router
) : BasePresenterImpl<MainView>() {

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
    }

}