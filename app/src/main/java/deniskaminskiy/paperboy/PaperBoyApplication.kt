package deniskaminskiy.paperboy

import androidx.multidex.MultiDexApplication
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.utils.ContextDelegateFactory

class PaperBoyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        ApiService.init(ContextDelegateFactory.create(this))
    }

}