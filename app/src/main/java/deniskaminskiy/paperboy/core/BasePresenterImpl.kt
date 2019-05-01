package deniskaminskiy.paperboy.core

import java.lang.ref.WeakReference

abstract class BasePresenterImpl<V : View>(
    view: V
) : Presenter<V> {

    private var viewRef: WeakReference<V?> = WeakReference(view)

    protected val view: V?
        get() = viewRef.get()

    protected var isAnimationRunning: Boolean = false

    override fun onViewAttached(view: V) {
        viewRef = WeakReference(view)
    }

    override fun onStart(viewCreated: Boolean) {
        //..
    }

    override fun onStop() {
        //..
    }

    override fun onViewDetached() {
        viewRef = WeakReference(null)
    }

    override fun onFinish() {
        //..
    }

    override fun onAnimationStart() {
        isAnimationRunning = true
    }

    override fun onAnimationEnd() {
        isAnimationRunning = false
    }

}