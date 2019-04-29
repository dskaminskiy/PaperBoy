package deniskaminskiy.paperboy.presentation.auth.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.intro.choose.ChooseChannelsFragment
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.presentation.view.TopPopupView
import deniskaminskiy.paperboy.utils.*
import deniskaminskiy.paperboy.utils.view.gone
import deniskaminskiy.paperboy.utils.view.visible
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

        presenter = AuthSecurityCodePresenter(this, ContextDelegateFactory.create(this), ColorsFactory.create(this))
            .apply {
                vBack.setOnClickListener { onBackClick() }
                vNext.setOnClickListener { onNextClick() }

                ivPasscode.onTextChanged = ::onSecurityCodeTextChanged
            }

        ivPasscode.post {
            ivPasscode.requestFocus()
        }
    }

    override fun show(isNextButtonEnable: Boolean) {
        vLoading.gone()
        vNext.isEnabled = isNextButtonEnable
    }

    override fun showLoading() {
        vLoading.visible()
    }

    override fun close() {
        onBackPressed()
    }

    override fun showError(model: TopPopupPresentModel) {
        vTopPopup.showWithAnimation(model,
            object : TopPopupView.OnPopupAnimationListener {
                override fun onAnimationStart() {
                    presenter?.onAnimationStart()
                }

                override fun onAnimationEnd() {
                    presenter?.onAnimationEnd()
                }
            })
    }

    override fun showImportChannels() {
        hideKeyboard()
        ChooseChannelsFragment.newInstance(true)
            .replace(activity, R.id.vgContent, ChooseChannelsFragment.TAG)
    }

}