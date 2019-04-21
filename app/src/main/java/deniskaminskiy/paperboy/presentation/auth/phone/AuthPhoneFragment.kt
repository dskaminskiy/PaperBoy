package deniskaminskiy.paperboy.presentation.auth.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
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

        presenter = AuthPhonePresenter(this).also { presenter ->
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

    override fun onDetach() {
        activity?.supportFragmentManager?.removeOnBackStackChangedListener(onBackStackChangedListener)
        super.onDetach()
    }

    override fun show(model: AuthPhonePresentModel) {
        ivRegion.text = model.regionAdditionalNumber
        ivPhone.text = model.phoneNumber
    }

    override fun showAuthCode() {
        AuthCodeFragment.newInstance()
            .open(activity, R.id.vgContent, AuthCodeFragment.TAG)
    }

}