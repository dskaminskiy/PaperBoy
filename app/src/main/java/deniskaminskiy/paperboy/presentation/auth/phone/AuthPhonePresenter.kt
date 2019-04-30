package deniskaminskiy.paperboy.presentation.auth.phone

import android.content.Context
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractor
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractorImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.disposeIfNotNull
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.Disposable

class AuthPhonePresenter(
    view: AuthPhoneView,
    private val contextDelegate: ContextDelegate,
    private val maxLengthReign: Int = MAX_LENGTH_REIGN,
    private val maxLengthPhone: Int = MAX_LENGTH_PHONE,
    private val interactor: AuthPhoneInteractor =
        AuthPhoneInteractorImpl(maxLengthReign, maxLengthPhone, contextDelegate),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val mapper: Mapper<AuthPhone, AuthPhonePresentModel> =
        AuthPhoneToPresentModelMapper(maxLengthPhone)
) : BasePresenterImpl<AuthPhoneView>(view) {

    companion object {
        private const val MAX_LENGTH_PHONE = 10
        private const val MAX_LENGTH_REIGN = 3
    }

    private var disposableCode: Disposable? = null
    private var disposableUpdateUi: Disposable? = null

    override fun onStart(viewCreated: Boolean) {
        super.onStart(viewCreated)
        disposableUpdateUi = interactor.onUiUpdateRequest()
            .map(mapper::map)
            .compose(composer.observable())
            .subscribe {
                view?.show(it)
            }
    }

    override fun onViewDetached() {
        disposableCode.disposeIfNotNull()
        disposableUpdateUi.disposeIfNotNull()
        super.onViewDetached()
    }

    fun onNextClick() {
        disposableCode = interactor.requestCode()
            .compose(composer.completable())
            .doOnSubscribe { view?.showLoading() }
            .doOnComplete { view?.hideLoading() }
            .subscribe {
                view?.showAuthCode()
            }
    }

    fun onReignAdditionalNumberChanged(newNumber: String) {
        interactor.onReignAdditionalNumberChanged(newNumber)
    }


    fun onPhoneNumberChanged(newNumber: String) {
        interactor.onPhoneNumberChanged(newNumber)
    }

}