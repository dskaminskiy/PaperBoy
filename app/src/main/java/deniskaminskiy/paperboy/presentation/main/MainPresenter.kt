package deniskaminskiy.paperboy.presentation.main

import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import deniskaminskiy.paperboy.core.BasePresenterImpl
import deniskaminskiy.paperboy.utils.ContextDelegate
import deniskaminskiy.paperboy.utils.ErrorFactory
import deniskaminskiy.paperboy.utils.rx.Composer
import deniskaminskiy.paperboy.utils.rx.SchedulerComposerFactory

class MainPresenter(
    view: MainView,
    private val contextDelegate: ContextDelegate,
    private val composer: Composer = SchedulerComposerFactory.android()
) : BasePresenterImpl<MainView>(view) {

    override fun onStart(isViewCreated: Boolean) {
        super.onStart(isViewCreated)

        if (isViewCreated) {
            view?.showAuth()
        }

        disposableUpdateUi = ReactiveNetwork
            .observeNetworkConnectivity(contextDelegate.getContext())
            .compose(composer.observable())
            .subscribe({
                if (it.state() == NetworkInfo.State.DISCONNECTED) {
                    showCustomTopPopupError(ErrorFactory.errorNoNetworkConnection)
                }
            }, ::defaultOnError)
    }

}