package deniskaminskiy.paperboy.presentation.auth.phone

import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractor
import deniskaminskiy.paperboy.domain.auth.AuthPhoneInteractorImpl
import deniskaminskiy.paperboy.presentation.view.TopPopupPresentModel
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.disposeIfNotNull
import deniskaminskiy.paperboy.utils.icon.IconConstant
import deniskaminskiy.paperboy.utils.icon.IconFactory
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import deniskaminskiy.paperboy.utils.managers.ResourcesManager
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory
import io.reactivex.disposables.Disposable

class AuthPhonePresenter(
    view: AuthPhoneView,
    private val contextDelegate: ContextDelegate,
    private val resources: ResourcesManager = AndroidResourcesManager.create(contextDelegate),
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

    private val unknownError: TopPopupPresentModel by lazy {
        TopPopupPresentModel(
            title = resources.strings.sometimesShitHappens,
            subtitle = resources.strings.sometimesShitHappens,
            icon = IconFactory.create(IconConstant.WARNING.constant),
            iconColor = resources.colors.marlboroNew
        )
    }

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
        super.onViewDetached()
    }

    fun onNextClick() {
        view?.showAuthCode()
//        disposableCode = interactor.requestCode()
//            .compose(composer.observable())
//            .doOnSubscribe { view?.showLoading() }
//            .doOnComplete { view?.hideLoading() }
//            .subscribe ({
//               with(it) {
//                   ifAuthorized { view?.showImportChannels() }
//                   ifError { view?.showTopPopup(unknownError) }
//                   ifWaitingForCode { view?.showAuthCode() }
//               }
//            }, {
//                view?.showTopPopup(unknownError)
//            })
    }

    fun onReignAdditionalNumberChanged(newNumber: String) {
        interactor.onReignAdditionalNumberChanged(newNumber)
    }

    fun onPhoneNumberChanged(newNumber: String) {
        interactor.onPhoneNumberChanged(newNumber)
    }

}

class AuthPhoneToPresentModelMapper(
    private val maxLengthPhone: Int
) : Mapper<AuthPhone, AuthPhonePresentModel> {
    override fun map(from: AuthPhone): AuthPhonePresentModel =
        AuthPhonePresentModel(
            regionAdditionalNumber = from.region.takeIf { it != -1 }?.toReignFormat() ?: "+",
            phoneNumber = from.phone.takeIf { it != -1L }?.toFormatNumber() ?: "",
            isNextButtonEnable = from.phone.toString().length == maxLengthPhone
                    && from.region.toString().isNotEmpty() && from.region != -1
        )

    private fun Long.toFormatNumber(): String = this.toString()
    private fun Int.toReignFormat(): String = "+$this"
}