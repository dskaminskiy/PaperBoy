package deniskaminskiy.paperboy.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
abstract class BaseActivity<P : Presenter<V>, V : View> : AppCompatActivity() {

    companion object {
        private const val TAG = "BaseActivity"
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