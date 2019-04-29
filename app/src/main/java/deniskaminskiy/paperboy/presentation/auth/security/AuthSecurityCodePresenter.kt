package deniskaminskiy.paperboy.presentation.auth.security

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

class AuthSecurityCodePresenter(
    view: AuthSecurityCodeView,
    private val contextDelegate: ContextDelegate,
    private val colors: Colors,
    private val repository: AuthRepository = AuthRepositoryFactory.create(),
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<AuthSecurityCodeView>(view) {

    companion object {
        private const val SETTINGS_FILE_NAME = "authSettings"
        private const val USER_TOKEN = "USER_TOKEN"
    }

    private var securityCode: String = ""

    private var isAnimationRunning = false
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
                if (it == AuthResponseState.AUTHORIZED) {
                    view?.showImportChannels()
                } else {
                    view?.showError(
                        TopPopupPresentModel(
                            "Обнаружена кратковременная память",
                            "Подумайте лучше",
                            icon = IconFactory.create(IconConstant.TRASH.constant),
                            iconColor = colors.marlboroNew
                        )
                    )
                }
            }, {
                view?.showError(
                    TopPopupPresentModel(
                        "Присядь, есть новость",
                        "Что-то пошло не так, допиваю чай и щас гляну",
                        icon = IconFactory.create(IconConstant.TRASH.constant),
                        iconColor = colors.marlboroNew
                    )
                )
            })

        view?.showImportChannels()
    }

    fun onSecurityCodeTextChanged(newCode: String) {
        securityCode = newCode
    }

    fun onAnimationStart() {
        isAnimationRunning = true
    }

    fun onAnimationEnd() {
        isAnimationRunning = false
    }

}