package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BasePresenterImpl

class AuthCodePresenter(
    view: AuthCodeView
) : BasePresenterImpl<AuthCodeView>(view) {

    companion object {
        private const val CODE_LENGTH = 5
    }

    private var code = ""

    private var isInputsUpdating = false
    private var isAnimationRunning = false

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        updateView()
    }

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String) {
        if (!isInputsUpdating) {
            if (newNumber.isNotEmpty()) {
                code += newNumber
                if (code.length >= CODE_LENGTH) {
                    //view?.showImportChannels()
                    clearInputs()
                    view?.showAuthSecurityCode()
                }
            } else {
                if (code.isNotEmpty()) {
                    code = code.dropLast(1)
                }
            }
            updateView()
        }
    }

    private fun clearInputs() {
        code = ""
        updateView()
    }

    fun onSendSmsClick() {
        //view?.showSmsSended()
        view?.showError()
    }

    private fun updateView() {
        isInputsUpdating = true
        view?.show(AuthCodePresentModel(code))
    }

    fun onInputsUpdateFinish() {
        isInputsUpdating = false
    }

    fun onBackClick() {
        view?.close()
    }

    fun onAnimationStart() {
        isAnimationRunning = true
    }

    fun onAnimationEnd() {
        isAnimationRunning = false
    }

    fun onBackspacePressedWithEmptyText() {
        if (code.isNotEmpty()) {
            code = code.dropLast(1)
            updateView()
        }
    }

}