package deniskaminskiy.paperboy.presentation.auth.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.intro.choose.ChooseChannelsFragment
import deniskaminskiy.paperboy.utils.hideKeyboard
import deniskaminskiy.paperboy.utils.replace
import kotlinx.android.synthetic.main.fragment_auth_security_code.*

class AuthSecurityCodeFragment : BaseFragment<AuthSecurityCodePresenter, AuthSecurityCodeView>(),
    AuthSecurityCodeView {

    companion object {
        const val TAG = "AuthSecurityCodeFragment"

        fun newInstance() = AuthSecurityCodeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_auth_security_code, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = AuthSecurityCodePresenter(this).apply {
            vBack.setOnClickListener { onBackClick() }
            vNext.setOnClickListener { onNextClick() }
        }

        ivPasscode.post {
            ivPasscode.requestFocus()
        }
    }

    override fun close() {
        onBackPressed()
    }

    override fun showImportChannels() {
        hideKeyboard()
        ChooseChannelsFragment.newInstance(true)
            .replace(activity, R.id.vgContent, ChooseChannelsFragment.TAG)
    }

}