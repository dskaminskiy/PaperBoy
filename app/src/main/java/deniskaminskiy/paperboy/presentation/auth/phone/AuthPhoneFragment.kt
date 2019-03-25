package deniskaminskiy.paperboy.presentation.auth.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import kotlinx.android.synthetic.main.view_input_phone_number.*

class AuthPhoneFragment : BaseFragment<AuthPhonePresenter, AuthPhoneView>(), AuthPhoneView {

    companion object {
        const val TAG = "AuthPhoneActivity"

        fun newInstance() = AuthPhoneFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_auth_phone, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AuthPhonePresenter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vNumber.requestFocus()
    }

}