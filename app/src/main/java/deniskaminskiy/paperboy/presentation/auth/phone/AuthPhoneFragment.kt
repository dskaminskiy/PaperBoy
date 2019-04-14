package deniskaminskiy.paperboy.presentation.auth.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.auth.code.AuthCodeFragment
import deniskaminskiy.paperboy.utils.open
import kotlinx.android.synthetic.main.fragment_auth_phone.*

class AuthPhoneFragment : BaseFragment<AuthPhonePresenter, AuthPhoneView>(), AuthPhoneView {

    companion object {
        const val TAG = "AuthPhoneFragment"

        fun newInstance() = AuthPhoneFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_auth_phone, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = AuthPhonePresenter(this).also {
            ivRegion.onTextChanged = it::onReignAdditionalNumberChanged
            ivPhone.onTextChanged = it::onPhoneNumberChanged
        }

        ivRegion.onFocusChanged = { hasFocus ->
            if (hasFocus) ivRegion.setSelectionToEnd()
        }

        ivPhone.requestFocus()
    }

    override fun show(model: AuthPhonePresentModel) {
        ivRegion.text = model.regionAdditionalNumber
        ivPhone.text = model.phoneNumber
    }

    override fun showAuthCode() {
        AuthCodeFragment.newInstance()
            .open(activity, android.R.id.content, AuthCodeFragment.TAG)
    }

}