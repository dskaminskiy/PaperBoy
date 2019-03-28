package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BasePresenterImpl

class AuthCodePresenter(
    view: AuthCodeView
) : BasePresenterImpl<AuthCodeView>(view) {

    private var code = ""

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        updateView()
    }

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String) {
        if (newNumber.isNotEmpty()) {
            code += newNumber
        } else {
            if (code.isNotEmpty()) {
                code = code.dropLast(1)
            }
        }
        updateView()
    }

    private fun updateView() {
        view?.show(AuthCodePresentModel(code))
    }

    fun onBackClick() {
        view?.close()
    }

}