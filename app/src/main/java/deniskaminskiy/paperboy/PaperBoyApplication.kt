package deniskaminskiy.paperboy

import androidx.multidex.MultiDexApplication
import deniskaminskiy.paperboy.data.AppDatabase
import deniskaminskiy.paperboy.data.api.ApiService
import deniskaminskiy.paperboy.utils.ContextDelegateFactory

class PaperBoyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        ApiService.init(ContextDelegateFactory.create(this))
        AppDatabase.init(this)
    }

}


/**
 * Cross to arrow animation

 private boolean isCrossShowing = true;

private void toggleAnimation(){
    AnimatedVectorDrawableCompat animDrawable = AnimatedVectorDrawableCompat.create(this, isCrossShowing ? R.drawable.avd_cross_to_back_arrow : R.drawable.avd_back_arrow_to_cross);
    imageView.setImageDrawable(animDrawable);
    currentDrawable.start();
    isCrossShowing = !isCrossShowing;
}

 */