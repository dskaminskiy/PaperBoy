package deniskaminskiy.paperboy.presentation.intro.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.utils.args

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_choose_channels, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}