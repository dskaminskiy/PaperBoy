package deniskaminskiy.paperboy.presentation.auth.security

import deniskaminskiy.paperboy.core.BasePresenterImpl

class AuthSecurityCodePresenter(
    view: AuthSecurityCodeView
) : BasePresenterImpl<AuthSecurityCodeView>(view) {

    fun onBackClick() {
        view?.close()
    }

    fun onNextClick() {
        view?.showImportChannels()
    }

}