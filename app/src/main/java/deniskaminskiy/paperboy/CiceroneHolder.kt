package deniskaminskiy.paperboy

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

interface CiceroneHolder {

    fun getNavigatorHolder(): NavigatorHolder

    fun getRouter(): Router

}