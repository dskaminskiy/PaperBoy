package deniskaminskiy.paperboy.presentation.auth.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.auth.code.AuthCodeFragment
import deniskaminskiy.paperboy.presentation.intro.choose.ChooseImportChannelsFragment
import deniskaminskiy.paperboy.utils.ContextDelegateFactory
import deniskaminskiy.paperboy.utils.hideKeyboard
import deniskaminskiy.paperboy.utils.replace
import deniskaminskiy.paperboy.utils.view.gone
import deniskaminskiy.paperboy.utils.view.visible
import kotlinx.android.synthetic.main.fragment_auth_phone.*

class AuthPhoneFragment : BaseFragment<AuthPhonePresenter, AuthPhoneView>(), AuthPhoneView {

    companion object {
        const val TAG = "AuthPhoneFragment"

        fun newInstance() = AuthPhoneFragment()
    }

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        if (activity?.supportFragmentManager?.fragments?.last() == this) {
            ivPhone.post {
                ivPhone.requestFocus()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_auth_phone, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = AuthPhonePresenter(
            view = this,
            contextDelegate = ContextDelegateFactory.create(this)
        ).also { presenter ->
            ivRegion.onTextChanged = presenter::onReignAdditionalNumberChanged
            ivPhone.onTextChanged = presenter::onPhoneNumberChanged
            vNext.setOnClickListener { presenter.onNextClick() }
        }

        ivRegion.onFocusChanged = { hasFocus ->
            if (hasFocus) ivRegion.setSelectionToEnd()
        }

        ivPhone.post {
            ivPhone.requestFocus()
        }

        activity?.supportFragmentManager?.addOnBackStackChangedListener(onBackStackChangedListener)
    }

    override fun onDestroyView() {
        activity?.supportFragmentManager?.removeOnBackStackChangedListener(onBackStackChangedListener)
        super.onDestroyView()
    }

    override fun show(model: AuthPhonePresentModel) {
        hideLoading()
        ivRegion.text = model.regionAdditionalNumber
        ivPhone.text = model.phoneNumber
        vNext.isEnabled = model.isNextButtonEnable
    }

    override fun showLoading() {
        vLoading.visible()
    }

    override fun hideLoading() {
        vLoading.gone()
    }

    override fun showAuthCode() {
        AuthCodeFragment.newInstance()
            .replace(activity, R.id.vgContent, AuthCodeFragment.TAG)
    }

    override fun showImportChannels() {
        hideKeyboard()
        ChooseImportChannelsFragment.newInstance()
            .replace(activity, R.id.vgContent, ChooseImportChannelsFragment.TAG)
    }

    override fun showInputError() {
        ivPhone.showError()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        super.onBackPressed()
    }

}