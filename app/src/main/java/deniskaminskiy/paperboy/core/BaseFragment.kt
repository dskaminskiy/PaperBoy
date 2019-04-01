package deniskaminskiy.paperboy.core

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

abstract class BaseFragment<P : Presenter<V>, V : View> : Fragment(), View,
    FragmentTransactionAllowable, BackPressedListener {

    companion object {
        private const val TAG = "BaseFragment"
    }

    protected var presenter: P? = null

    private var isFirstStart: Boolean = true
    private var transactionAllowable: FragmentTransactionAllowable? = null

    override val isTransactionAllowed: Boolean
        get() = isAdded && transactionAllowable?.isTransactionAllowed == true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFirstStart = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? FragmentTransactionAllowable)?.let {
            transactionAllowable = it
        }
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

    override fun onBackPressed() {
        activity?.let {
            if (it is BaseActivity<*,*>) {
                it.backPress()
            } else {
                it.onBackPressed()
            }
        }
    }

    fun showKeyboard(view: android.view.View) {
        activity?.let {
            if (it is BaseActivity<*, *>) {
                it.showKeyboard(view)
            } else {
                (it.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(view, 0)
            }
        }
    }

}