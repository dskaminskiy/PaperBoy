package deniskaminskiy.paperboy.core

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

@SuppressLint("Registered")
abstract class BaseActivity<P : Presenter<V>, V : View> : AppCompatActivity(), View,
    FragmentTransactionAllowable {

    companion object {
        private const val TAG = "BaseActivity"
    }

    protected var presenter: P? = null

    private var isFirstStart: Boolean = true
    protected var isFragmentTransactionAllowed = false

    private val backStackCount: Int
        get() = supportFragmentManager?.backStackEntryCount ?: 0

    private val existingFragment: BaseFragment<*, *>?
        get() = supportFragmentManager.fragments
            .takeIf { it.isNotEmpty() }
            ?.lastOrNull { it is BaseFragment<*,*> }
            ?.let { it as BaseFragment<*, *> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFragmentTransactionAllowed = true
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

    override fun onResumeFragments() {
        isFragmentTransactionAllowed = true
        super.onResumeFragments()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        isFragmentTransactionAllowed = false
        super.onSaveInstanceState(outState)
    }

    override val isTransactionAllowed: Boolean
        get() = isFragmentTransactionAllowed

    private fun getActiveFragment(): Fragment? {
        val stackEntryCount = supportFragmentManager.backStackEntryCount

        if (stackEntryCount < 1) {
            return null
        }

        return supportFragmentManager.getBackStackEntryAt(stackEntryCount - 1)
            .name
            .let(supportFragmentManager::findFragmentByTag)
    }

    fun showKeyboard(view: android.view.View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(view, 0)
    }

    override fun onBackPressed() {
        val currentFragment = getActiveFragment() ?: existingFragment

        if (currentFragment != null && currentFragment is BackPressedListener) {
            currentFragment.onBackPressed()
        } else {
            backPress()
        }
    }

    fun backPress() {
        onBackPressed()
    }

}