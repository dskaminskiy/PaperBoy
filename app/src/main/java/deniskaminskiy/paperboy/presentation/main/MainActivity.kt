package deniskaminskiy.paperboy.presentation.main

import android.os.Bundle
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseActivity
import deniskaminskiy.paperboy.core.OnAnimationLifecycleListener
import deniskaminskiy.paperboy.presentation.home.HomeFragment
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.open
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    override val isTransactionAllowed: Boolean
        get() = isFragmentTransactionAllowed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
    }

    override fun showAuth() {
        HomeFragment.newInstance()
            .open(this, R.id.vgContent, HomeFragment.TAG)
    }

    override fun showTopPopup(model: TopPopupPresentModel, animListener: OnAnimationLifecycleListener?) {
        vTopPopup.showWithAnimation(model, animListener)
    }

}

