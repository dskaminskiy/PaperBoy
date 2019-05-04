package deniskaminskiy.paperboy.presentation.auth.security

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.domain.auth.AuthSecurityCodeInteractor
import deniskaminskiy.paperboy.domain.auth.AuthSecurityCodeInteractorImpl
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.disposeIfNotNull
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.Disposable

class AuthSecurityCodePresenter(
    view: AuthSecurityCodeView,
    private val contextDelegate: ContextDelegate,
    private val resources: ResourcesManager,
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val interactor: AuthSecurityCodeInteractor =
        AuthSecurityCodeInteractorImpl(contextDelegate)
) : BasePresenterImpl<AuthSecurityCodeView>(view) {

    private val unknownError: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources.strings.sometimesShitHappens,
            subtitle = resources.strings.sometimesShitHappens,
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources.colors.marlboroNew
        )
    }

    private var disposableSecurityCode: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)

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
        disposableSecurityCode = interactor.sendSecurityCode()
            .compose(composer.observable())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribe({
                with(it) {
                    ifAuthorized { view?.showImportChannels() }
                    ifError { view?.showTopPopup(unknownError) }
                }
            }, {
                view?.showTopPopup(unknownError)
            })
    }

    fun onSecurityCodeTextChanged(newCode: String) {
        interactor.onSecurityCodeTextChanged(newCode)
    }

}