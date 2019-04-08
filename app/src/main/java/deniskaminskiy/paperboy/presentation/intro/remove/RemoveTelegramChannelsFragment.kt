package deniskaminskiy.paperboy.presentation.intro.remove

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment

class RemoveTelegramChannelsFragment : BaseFragment<RemoveTelegramChannelsPresenter, RemoveTelegramChannelsView>(),
    RemoveTelegramChannelsView {

    companion object {
        const val TAG = "RemoveTelegramChannelsFragment"

        fun newInstance() = RemoveTelegramChannelsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_remove_telegram_channels, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = RemoveTelegramChannelsPresenter(this)
    }

}
