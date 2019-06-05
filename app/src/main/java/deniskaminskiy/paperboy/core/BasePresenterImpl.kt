package deniskaminskiy.paperboy.core

import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.ErrorFactory
import deniskaminskiy.paperboy.utils.api.fold
import deniskaminskiy.paperboy.utils.api.responseOrError
import deniskaminskiy.paperboy.utils.icon.Icon
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BasePresenterImpl<V : View>(
    view: V
) : Presenter<V> {

    private var viewRef: WeakReference<V?> = WeakReference(view)

    protected val view: V?
        get() = viewRef.get()

    protected var isAnimationRunning: Boolean = false

    /**
     * For interactor updateUi subscribe method
     */
    protected var disposableUpdateUi: Disposable? = null

    override fun onViewAttached(view: V) {
        viewRef = WeakReference(view)
    }

    /**
     * @param viewCreated   - true if it's first calling presenter onStart()
     */
    override fun onStart(viewCreated: Boolean) {
        //..
    }

    override fun onStop() {
        //..
    }

    override fun onViewDetached() {
        disposableUpdateUi.disposeIfNotNull()
        viewRef = WeakReference(null)
    }

    override fun onDestroy() {
        //..
    }

    override fun onAnimationStart() {
        isAnimationRunning = true
    }

    override fun onAnimationEnd() {
        isAnimationRunning = false
    }

    protected fun onError(t: Throwable) {
        t.responseOrError()
            .fold({
                showCustomTopPopupError(subtitle = it.message)
            }, {
                showUnknownTopPopupError()
            })
    }

    protected fun showUnknownTopPopupError() {
        view?.showTopPopup(ErrorFactory.unknownError)
    }

    private fun showCustomTopPopupError(
        title: String = "",
        subtitle: String = "",
        icon: Icon? = null,
        @ColorInt iconColor: Int = -1
    ) {
        view?.showTopPopup(
            ErrorFactory.unknownError.let { default ->
                TopPopupPresentModel(
                    title = if (title.isNotBlank()) title else default.title,
                    subtitle = if (subtitle.isNotBlank()) subtitle else default.subtitle,
                    icon = icon ?: default.icon,
                    iconColor = if (iconColor != -1) iconColor else default.iconColor
                )
            }
        )
    }

}