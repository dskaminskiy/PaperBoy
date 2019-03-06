package deniskaminskiy.paperboy.core

import android.os.Bundle
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<P : Presenter<V>, V : View> : DialogFragment(), View {

    companion object {
        private const val TAG = "BaseDialogFragment"
    }

    protected var presenter: P? = null

    private var isFirstStart: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFirstStart = true
    }

    @Suppress("UNCHECKED_CAST")
    override fun onStart() {
        super.onStart()
        if (presenter != null) {
            presenter?.onViewAttached(this as V)
            presenter?.onStart(isFirstStart)
            isFirstStart = false
        }
    }

    override fun onStop() {
        if (presenter != null) {
            presenter?.onStop()
            presenter?.onViewDetached()
        }
        super.onStop()
    }

}
