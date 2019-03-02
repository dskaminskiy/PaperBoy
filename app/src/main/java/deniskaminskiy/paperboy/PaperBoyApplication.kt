package deniskaminskiy.paperboy

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class PaperBoyApplication : Application(), CiceroneHolder {

    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }

    override fun getRouter(): Router = cicerone.router

    override fun getNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder

}