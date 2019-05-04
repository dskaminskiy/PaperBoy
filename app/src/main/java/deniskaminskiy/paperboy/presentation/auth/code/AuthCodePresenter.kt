package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.data.api.ifWaitingForPassword
import deniskaminskiy.paperboy.domain.auth.AuthCodeInteractor
import deniskaminskiy.paperboy.domain.auth.AuthCodeInteractorImpl
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
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
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val codeLength: Int = CODE_LENGTH,
    private val mapper: Mapper<String, AuthCodePresentModel> = CodeStringToAuthCodePresentModelMapper(),
    private val interactor: AuthCodeInteractor = AuthCodeInteractorImpl(codeLength)
) : BasePresenterImpl<AuthCodeView>(view) {

    companion object {
        private const val CODE_LENGTH = 5
    }

    private val unknownError: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources.strings.sometimesShitHappens,
            subtitle = resources.strings.sometimesShitHappens,
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources.colors.marlboroNew
        )
    }

    private var isInputsUpdating = false
    private var disposableSendCode: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

        disposableUpdateUi = interactor.onUiUpdateRequest()
            .map(mapper::map)
            .compose(composer.observable())
            .subscribe {
                isInputsUpdating = true
                view?.show(it)
            }

        interactor.onFullCodeEntered = { sendCode() }
    }

    override fun onViewDetached() {
        disposableSendCode.disposeIfNotNull()
        super.onViewDetached()
    }

    private fun sendCode() {
        disposableSendCode = interactor.sendCode()
            .compose(composer.observable())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { interactor.clearCode() }
            .subscribe({
                with(it) {
                    ifAuthorized { view?.showImportChannels() }
                    ifError { showError() }
                    ifWaitingForPassword { view?.showAuthSecurityCode() }
                }
            }, {
                showError()
            })
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
            interactor.onPassCodeChanged(newNumber)
        }
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
        interactor.removeLastCodeSymbol()
    }

}

class CodeStringToAuthCodePresentModelMapper : Mapper<String, AuthCodePresentModel> {
    override fun map(from: String): AuthCodePresentModel =
        AuthCodePresentModel(from)
}