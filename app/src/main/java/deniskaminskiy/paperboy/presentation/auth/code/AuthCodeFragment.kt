package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BaseFragment

class AuthCodeFragment : BaseFragment<AuthCodePresenter, AuthCodeView>(), AuthCodeView {

    companion object {
        const val TAG = "AuthCodeFragment"

        fun newInstance() = AuthCodeFragment()
    }

}