package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.data.api.ifAuthorized
import deniskaminskiy.paperboy.data.api.ifError
import deniskaminskiy.paperboy.data.api.ifWaitingForCode
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractor
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractorImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import deniskaminskiy.paperboy.utils.rx.disposeIfNotNull
import io.reactivex.disposables.Disposable

class AuthPhonePresenter(
    view: AuthPhoneView,
    private val contextDelegate: ContextDelegate,
    private val maxLengthReign: Int = MAX_LENGTH_REIGN,
    private val maxLengthPhone: Int = MAX_LENGTH_PHONE,
    private val interactor: AuthPhoneInteractor =
        AuthPhoneInteractorImpl(maxLengthReign, maxLengthPhone, contextDelegate),
    private val composer: Composer = SchedulerComposerFactory.android(),
    private val mapper: Mapper<AuthPhone, AuthPhonePresModel> =
        AuthPhoneToPresModelMapper(maxLengthPhone)
) : BasePresenterImpl<AuthPhoneView>(view) {

    companion object {
        private const val MAX_LENGTH_PHONE = 10
        private const val MAX_LENGTH_REIGN = 3
    }

    private var disposableCode: Disposable? = null

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)

        disposableUpdateUi.disposeIfNotNull()
        disposableUpdateUi = interactor.onModelUpdate()
            .map(mapper::map)
            .compose(composer.observable())
            .subscribe {
                view?.show(it)
            }
    }

    override fun onViewDetached() {
        disposableCode.disposeIfNotNull()
        super.onViewDetached()
    }

    fun onNextClick() {
        disposableCode.disposeIfNotNull()
        disposableCode = interactor.requestCode()
            .compose(composer.observable())
            .doOnSubscribe { view?.showLoading() }
            .doOnEach { view?.hideLoading() }
            .subscribe({
                with(it) {
                    ifAuthorized {
                        //TODO: костыль, ибо может прийти этот статус, но по факту это WAITING_FOR_CODE
                        view?.showAuthCode()
                    }
                    ifError {
                        view?.showInputError()
                        showUnknownTopPopupError()
                    }
                    ifWaitingForCode {
                        view?.showAuthCode()
                    }
                }
            }, ::defaultOnError)
    }

    fun onReignAdditionalNumberChanged(newNumber: String) {
        interactor.onReignAdditionalNumberChanged(newNumber)
    }

    fun onPhoneNumberChanged(newNumber: String) {
        interactor.onPhoneNumberChanged(newNumber)
    }

}

class AuthPhoneToPresModelMapper(
    private val maxLengthPhone: Int
) : Mapper<AuthPhone, AuthPhonePresModel> {
    override fun map(from: AuthPhone): AuthPhonePresModel =
        AuthPhonePresModel(
            regionAdditionalNumber = from.region.takeIf { it != -1 }?.toRegionFormat() ?: "+",
            phoneNumber = from.phone.takeIf { it != -1L }?.toFormatNumber() ?: "",
            isNextButtonEnable = from.phone.toString().length == maxLengthPhone
                    && from.region.toString().isNotEmpty() && from.region != -1
        )

    private fun Long.toFormatNumber(): String = this.toString()
    private fun Int.toRegionFormat(): String = "+$this"
}