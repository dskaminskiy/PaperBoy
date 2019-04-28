package deniskaminskiy.paperboy.presentation.auth.code

import android.content.Context
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.data.api.AuthResponseState
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.Colors
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.disposeIfNotNull
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.Disposable

class AuthCodePresenter(
    view: AuthCodeView,
    private val colors: Colors,
    private val contextDelegate: ContextDelegate,
    private val repository: AuthRepository = AuthRepositoryFactory.create(),
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<AuthCodeView>(view) {

    companion object {
        private const val CODE_LENGTH = 5

        private const val SETTINGS_FILE_NAME = "authSettings"
        private const val USER_TOKEN = "USER_TOKEN"
    }

    private var code = ""

    private var isInputsUpdating = false
    private var isAnimationRunning = false

    private var disposableSendCode: Disposable? = null

    private val userToken: String
        get() = contextDelegate.getContext()?.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
            ?.getString(USER_TOKEN, "")
            ?: ""


    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        updateView()
    }

    override fun onViewDetached() {
        disposableSendCode.disposeIfNotNull()
        super.onViewDetached()
    }

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String) {
        if (!isInputsUpdating) {
            if (newNumber.isNotEmpty()) {
                code += newNumber
                if (code.length >= CODE_LENGTH) {
                    sendCode()
                }
            } else {
                if (code.isNotEmpty()) {
                    code = code.dropLast(1)
                }
            }
            updateView()
        }
    }

    private fun sendCode() {
        try {
            disposableSendCode = repository.sendCode(code.toInt(), userToken)
                .compose(composer.observable())
                .doOnSubscribe { view?.showLoading() }
                .doFinally { clearInputs() }
                .subscribe({
                    if (it == AuthResponseState.AUTHORIZED) {
                        view?.showImportChannels()
                    } else {
                        view?.showAuthSecurityCode()
                    }
                }, {
                    view?.showError(
                        TopPopupPresentModel(
                            "Что-то случилось",
                            "Щас доем бутер и гляну :(",
                            icon = IconFactory.create(IconConstant.TRASH.constant),
                            iconColor = colors.marlboroNew
                        )
                    )
                })
        } catch (e: NumberFormatException) {
            view?.showError(
                TopPopupPresentModel(
                    "Ах, да, чувак",
                    "Забыл сказать, что код состоит только из цифр :(",
                    icon = IconFactory.create(IconConstant.TRASH.constant),
                    iconColor = colors.marlboroNew
                )
            )
        }
    }

    private fun clearInputs() {
        code = ""
        updateView()
    }

    fun onSendSmsClick() {
        view?.showSmsSended()
        view?.showError(
            TopPopupPresentModel(
                "Присядь, есть новость",
                "Мы еще не умеем отправлять код по смс :(",
                icon = IconFactory.create(IconConstant.TRASH.constant),
                iconColor = colors.marlboroNew
            )
        )
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