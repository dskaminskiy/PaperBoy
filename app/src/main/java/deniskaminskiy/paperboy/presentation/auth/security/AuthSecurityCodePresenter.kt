package deniskaminskiy.paperboy.presentation.auth.security

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.domain.auth.AuthSecurityCodeInteractor
import deniskaminskiy.paperboy.domain.auth.AuthSecurityCodeInteractorImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull
import io.reactivex.disposables.Disposable

class AuthSecurityCodePresenter(
    view: AuthSecurityCodeView,
    private val contextDelegate: ContextDelegate,
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val interactor: AuthSecurityCodeInteractor =
        AuthSecurityCodeInteractorImpl(contextDelegate)
) : BasePresenterImpl<AuthSecurityCodeView>(view) {

    private var disposableSecurityCode: Disposable? = null
    private var disposableLoadImportChannels: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.onUiUpdateRequest()
            .compose(composer.observable())
            .subscribe { view?.show(it) }
    }

    override fun onViewDetached() {
        disposableSecurityCode.disposeIfNotNull()
        super.onViewDetached()
    }

    fun onBackClick() {
        view?.close()
    }

    fun onNextClick() {
        disposableSecurityCode.disposeIfNotNull()
        disposableSecurityCode = interactor.sendSecurityCode()
            .compose(composer.observable())
            .doOnSubscribe { view?.showLoading() }
            .subscribe({
                with(it) {
                    ifAuthorized {
                        fetchImportChannels()
                    }
                    ifError {
                        view?.hideLoading()
                        showUnknownTopPopupError()
                    }
                }
            }, { t ->
                view?.hideLoading()
                onError(t)
            })
    }

    private fun fetchImportChannels() {
        disposableLoadImportChannels.disposeIfNotNull()
        disposableLoadImportChannels = interactor.loadAndCacheImportChannels()
            .compose(composer.completable())
            .doOnError { view?.hideLoading() }
            .subscribe({
                view?.hideLoading()
                view?.showImportChannels()
            }, ::onError)
    }

    fun onSecurityCodeTextChanged(newCode: String) {
        interactor.onSecurityCodeTextChanged(newCode)
    }

}