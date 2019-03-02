package deniskaminskiy.paperboy.presentation.main

import android.os.Bundle
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseActivity
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : BaseActivity<MainPresenter, MainView>() {

    private val router by lazy { Cicerone.create().router }
    private val navigatorHolder by lazy { Cicerone.create().navigatorHolder }

    private val navigator = SupportAppNavigator(this, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(router)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}