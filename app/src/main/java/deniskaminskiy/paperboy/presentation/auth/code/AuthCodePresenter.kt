package deniskaminskiy.paperboy.presentation.auth.code

import android.content.Context
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.data.api.ifWaitingForPassword
import deniskaminskiy.paperboy.data.auth.AuthRepository
import deniskaminskiy.paperboy.data.auth.AuthRepositoryFactory
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.disposeIfNotNull
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.Disposable

class AuthCodePresenter(
    view: AuthCodeView,
    private val resources: ResourcesManager,
    private val contextDelegate: ContextDelegate,
    private val repository: AuthRepository = AuthRepositoryFactory.create(),
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<AuthCodeView>(view) {

    companion object {
        private const val CODE_LENGTH = 5

        private const val SETTINGS_FILE_NAME = "deniskaminskiy.paperboy.data.settings.App"
        private const val USER_TOKEN = "USER_TOKEN"
    }

    private val unknownError: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources.strings.sometimesShitHappens,
            subtitle = resources.strings.sometimesShitHappens,
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources.colors.marlboroNew
        )
    }

    private var code = ""

    private var isInputsUpdating = false

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

    private fun updateView() {
        isInputsUpdating = true
        view?.show(AuthCodePresentModel(code))
    }

    private fun sendCode() {
        try {
            disposableSendCode = repository.sendCode(code.toInt(), userToken)
                .compose(composer.observable())
                .doOnSubscribe { view?.showLoading() }
                .doFinally { clearInputs() }
                .subscribe({
                    with(it) {
                        ifAuthorized { view?.showImportChannels() }
                        ifError { showError() }
                        ifWaitingForPassword { view?.showAuthSecurityCode() }
                    }
                }, {
                    showError()
                })
        } catch (e: NumberFormatException) {
            showError()
        }
    }

    private fun showError() {
        view?.hideLoading()
        view?.showTopPopup(unknownError)
    }

    /**
     * @param newNumber     - should be integer; if empty string, then it is removing
     */
    fun onPassCodeChanged(newNumber: String) {
        if (!isInputsUpdating) {
            if (newNumber.isNotEmpty()) {
                code += newNumber
            } else {
                if (code.isNotEmpty()) {
                    code = code.dropLast(1)
                }
            }
            updateView()

            if (code.length >= CODE_LENGTH) {
                sendCode()
            }
        }
    }

    private fun clearInputs() {
        code = ""
        updateView()
    }

    fun onSendSmsClick() {
        view?.showSmsSended()
    }

    fun onInputsUpdateFinish() {
        isInputsUpdating = false
    }

    fun onBackClick() {
        view?.close()
    }

    fun onBackspacePressedWithEmptyText() {
        if (code.isNotEmpty()) {
            code = code.dropLast(1)
            updateView()
        }
    }

}