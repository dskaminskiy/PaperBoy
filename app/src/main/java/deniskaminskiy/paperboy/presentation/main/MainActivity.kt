package deniskaminskiy.paperboy.presentation.main

import android.os.Bundle
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseActivity
import deniskaminskiy.paperboy.presentation.auth.phone.AuthPhoneFragment
import deniskaminskiy.paperboy.utils.open

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    override val isTransactionAllowed: Boolean
        get() = isFragmentTransactionAllowed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
    }

    override fun showAuth() {
        AuthPhoneFragment.newInstance()
            .open(this, R.id.vgContent, AuthPhoneFragment.TAG)
    }

}