package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.data.api.ifWaitingForPassword
import deniskaminskiy.paperboy.domain.auth.AuthCodeInteractor
import deniskaminskiy.paperboy.domain.auth.AuthCodeInteractorImpl
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull
import io.reactivex.disposables.Disposable

class AuthCodePresenter(
    view: AuthCodeView,
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val codeLength: Int = CODE_LENGTH,
    private val mapper: Mapper<String, AuthCodePresentModel> = CodeStringToAuthCodePresentModelMapper(),
    private val interactor: AuthCodeInteractor = AuthCodeInteractorImpl(codeLength)
) : BasePresenterImpl<AuthCodeView>(view) {

    companion object {
        private const val CODE_LENGTH = 5
    }

    private var isInputsUpdating = false

    private var disposableSendCode: Disposable? = null
    private var disposableLoadImportChannels: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.onModelUpdate()
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
        disposableLoadImportChannels.disposeIfNotNull()
        super.onViewDetached()
    }

    private fun sendCode() {
        disposableSendCode.disposeIfNotNull()
        disposableSendCode = interactor.sendCode()
            .compose(composer.observable())
            .doOnSubscribe { view?.showLoading() }
            .doOnError { clearView() }
            .subscribe({
                with(it) {
                    ifAuthorized {
                        fetchImportChannels()
                    }
                    ifError {
                        clearView()
                        showUnknownTopPopupError()
                    }
                    ifWaitingForPassword {
                        clearView()
                        view?.showAuthSecurityCode()
                    }
                }
            }, ::onError)
    }

    private fun fetchImportChannels() {
        disposableLoadImportChannels.disposeIfNotNull()
        disposableLoadImportChannels = interactor.loadAndCacheImportChannels()
            .compose(composer.completable())
            .subscribe({
                clearView()
                view?.showImportChannels()
            }, { t ->
                clearView()
                onError(t)
            })
    }

    private fun clearView() {
        interactor.clearCode()
        view?.hideLoading()
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