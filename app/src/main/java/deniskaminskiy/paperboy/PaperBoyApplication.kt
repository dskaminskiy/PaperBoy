package deniskaminskiy.paperboy

import androidx.multidex.MultiDexApplication
import deniskaminskiy.paperboy.data.AppDatabase
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.utils.ContextDelegateFactory
import deniskaminskiy.paperboy.utils.ErrorFactory
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager

class PaperBoyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        val contextDelegate = ContextDelegateFactory.create(this)

        ApiService.init(contextDelegate)
        AppDatabase.init(this)
        ErrorFactory.init(AndroidResourcesManager.create(contextDelegate))

    }

}