package deniskaminskiy.paperboy.presentation.intro.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.utils.AndroidColors
import deniskaminskiy.paperboy.utils.ContextDelegateFactory
import deniskaminskiy.paperboy.utils.args
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import kotlinx.android.synthetic.main.fragment_choose_channels.*

class ChooseChannelsFragment : BaseFragment<ChooseChannelsPresenter, ChooseChannelsView>(), ChooseChannelsView {

    companion object {

        const val TAG = "ChooseChannelsFragment"

        const val ARG_CHANNELS_IS_FETCHED = "ARG_CHANNELS_IS_FETCHED"

        /**
         * @param isChannelsFetched         - флаг, указывающий на то, загрузились ли данные о каналах пользователя
         *                                  на предыдущем экране (закешированы)
         */
        fun newInstance(isChannelsFetched: Boolean = false) = ChooseChannelsFragment()
            .args {
                putBoolean(ARG_CHANNELS_IS_FETCHED, isChannelsFetched)
            }
    }

    private val isChannelsFetched: Boolean
        get() = arguments?.getBoolean(ARG_CHANNELS_IS_FETCHED, false) ?: false

    private val adapter = CheckItemAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_choose_channels, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = ChooseChannelsPresenter(this, isChannelsFetched, AndroidResourcesManager.create(this),
            AndroidColors(ContextDelegateFactory.create(this)))

        rvChannels.layoutManager = LinearLayoutManager(context)
        rvChannels.adapter = adapter
    }

    override fun show(model: ChooseChannelsPresentModel) {
        if (tvTitle.text.toString() != model.title.toString()) {
            tvTitle.text = model.title
        }

        if (tvSubtitle.text.toString() != model.subtitle) {
            tvSubtitle.text = model.subtitle
        }

        adapter.setData(model.channels)
    }

}