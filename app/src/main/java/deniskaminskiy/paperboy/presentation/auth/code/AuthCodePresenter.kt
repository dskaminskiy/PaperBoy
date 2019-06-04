package deniskaminskiy.paperboy.presentation.auth.code

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.data.api.ifWaitingForPassword
import deniskaminskiy.paperboy.domain.auth.AuthCodeInteractor
import deniskaminskiy.paperboy.domain.auth.AuthCodeInteractorImpl
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.api.fold
import deniskaminskiy.paperboy.utils.api.responseOrError
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
            title = resources.strings.somethingHappened,
            subtitle = resources.strings.sometimesShitHappens,
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources.colors.marlboroNew
        )
    }

    private var isInputsUpdating = false

    private var disposableSendCode: Disposable? = null
    private var disposableLoadImportChannels: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

        disposableUpdateUi = interactor.onModelUpdate()
            .map(mapper::map)
            .compose(composer.observable())
            .subscribe {
                isInputsUpdating = true
                view?.show(it)
            }

        interactor.onFullCodeEntered = { sendCode() }
    }

    override fun onDestroy() {
        disposableSendCode.disposeIfNotNull()
        disposableLoadImportChannels.disposeIfNotNull()
        super.onDestroy()
    }

    private fun sendCode() {
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
                        view?.showTopPopup(unknownError)
                    }
                    ifWaitingForPassword {
                        clearView()
                        view?.showAuthSecurityCode()
                    }
                }
            }, { t ->
                t.responseOrError()
                    .fold({
                        view?.showTopPopup(unknownError.copy(subtitle = it.message))
                    }, {
                        view?.showTopPopup(unknownError)
                    })
            })
    }

    private fun fetchImportChannels() {
        disposableLoadImportChannels = interactor.loadAndCacheImportChannels()
            .compose(composer.completable())
            .subscribe({
                clearView()
                view?.showImportChannels()
            }, { t ->
                clearView()
                t.responseOrError()
                    .fold({
                        view?.showTopPopup(unknownError.copy(subtitle = it.message))
                    }, {
                        view?.showTopPopup(unknownError)
                    })
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