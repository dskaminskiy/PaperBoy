package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BasePresenterImpl
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
    private val interactor: AuthCodeInteractor = AuthCodeInteractorImpl()
) : BasePresenterImpl<AuthCodeView>(view) {

    private var isInputsUpdating = false

    private var disposableSendCode: Disposable? = null
    private var disposableLoadImportChannels: Disposable? = null

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)

        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.onModelUpdate()
            .compose(composer.observable())
            .subscribe {
                isInputsUpdating = true
                view?.show(it)

//                if (it.code.length >= MAX_CODE_LENGTH) {
//                    sendCode()
//                }
            }
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
            }, ::defaultOnError)
    }

    private fun fetchImportChannels() {
        disposableLoadImportChannels.disposeIfNotNull()
        disposableLoadImportChannels = interactor.loadAndCacheImportChannels()
            .compose(composer.single())
            .subscribe({ isAtLeastOneChannel ->
                clearView()
                if (isAtLeastOneChannel) {
                    view?.showImportChannels()
                } else {

                }
            }, { t ->
                clearView()
                defaultOnError(t)
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
        if (!isInputsUpdating && disposableSendCode?.isDisposed != false) {
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