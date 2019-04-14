package deniskaminskiy.paperboy.presentation.intro.remove

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.utils.ColorsFactory
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import kotlinx.android.synthetic.main.fragment_remove_telegram_channels.*

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

        presenter = RemoveTelegramChannelsPresenter(
            view = this,
            resources = AndroidResourcesManager.create(this),
            colors = ColorsFactory.create(this))
    }

    override fun show(model: RemoveTelegramChannelsPresentModel) {
        tvTitle.text = model.title
    }

}
