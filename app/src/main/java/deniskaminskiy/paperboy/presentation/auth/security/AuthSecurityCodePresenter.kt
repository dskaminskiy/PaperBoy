package deniskaminskiy.paperboy.presentation.auth.security

import android.content.Context
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
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

class AuthSecurityCodePresenter(
    view: AuthSecurityCodeView,
    private val contextDelegate: ContextDelegate,
    private val resources: ResourcesManager,
    private val repository: AuthRepository = AuthRepositoryFactory.create(),
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<AuthSecurityCodeView>(view) {

    companion object {
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

    private var securityCode: String = ""

    private var disposableSecurityCode: Disposable? = null

    private val userToken: String
        get() = contextDelegate.getContext()?.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
            ?.getString(USER_TOKEN, "")
            ?: ""

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        updateView()
    }

    override fun onViewDetached() {
        disposableSecurityCode.disposeIfNotNull()
        super.onViewDetached()
    }

    private fun updateView() {
        view?.show(!securityCode.isBlank())
    }

    fun onBackClick() {
        view?.close()
    }

    fun onNextClick() {
        disposableSecurityCode = repository.sendSecurityCode(securityCode, userToken)
            .compose(composer.observable())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { updateView() }
            .subscribe({
                with(it) {
                    ifAuthorized { view?.showImportChannels() }
                    ifError { view?.showTopPopup(unknownError) }
                }
            }, {
                view?.showTopPopup(unknownError)
            })

        view?.showImportChannels()
    }

    fun onSecurityCodeTextChanged(newCode: String) {
        securityCode = newCode
    }

}