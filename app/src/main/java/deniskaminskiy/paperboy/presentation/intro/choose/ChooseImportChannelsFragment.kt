package deniskaminskiy.paperboy.presentation.intro.choose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.presentation.intro.remove.RemoveTelegramChannelsFragment
import deniskaminskiy.paperboy.utils.args
import deniskaminskiy.paperboy.utils.hideApp
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import deniskaminskiy.paperboy.utils.open
import deniskaminskiy.paperboy.utils.view.isGone
import deniskaminskiy.paperboy.utils.view.isVisible
import kotlinx.android.synthetic.main.fragment_choose_channels.*


class ChooseImportChannelsFragment : BaseFragment<ChooseImportChannelsPresenter, ChooseImportChannelsView>(), ChooseImportChannelsView {

    companion object {

        const val TAG = "ChooseImportChannelsFragment"

        const val ARG_CHANNELS_IS_FETCHED = "ARG_CHANNELS_IS_FETCHED"

        /**
         * @param isChannelsFetched         - флаг, указывающий на то, загрузились ли данные о каналах пользователя
         *                                  на предыдущем экране (закешированы)
         */
        fun newInstance(isChannelsFetched: Boolean = false) = ChooseImportChannelsFragment()
            .args {
                putBoolean(ARG_CHANNELS_IS_FETCHED, isChannelsFetched)
            }
    }

    private val isChannelsFetched: Boolean
        get() = arguments?.getBoolean(ARG_CHANNELS_IS_FETCHED, false) ?: false

    private val adapter = CheckItemAdapter<ImportChannel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_choose_channels, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = ChooseImportChannelsPresenter(
            view = this,
            isChannelsFetched = isChannelsFetched,
            resources = AndroidResourcesManager.create(this)
        ).apply {
            tvSkip.setOnClickListener { onSkipClick() }
            vFab.setOnClickListener { onFabClick() }
            adapter.onItemClick = ::onItemClick
        }

        rvChannels.layoutManager = LinearLayoutManager(context)
        rvChannels.adapter = adapter
    }

    override fun show(model: ChooseImportChannelsPresentModel) {
        tvTitle.text = model.title

        if (tvSubtitle.text.toString() != model.subtitle) {
            tvSubtitle.text = model.subtitle
        }

        adapter.setData(model.channels)

        if (model.isFabVisible && vFab.isGone) {
            vFab.show()
        } else if (!model.isFabVisible && vFab.isVisible) {
            vFab.hide()
        }
    }

    override fun showRemoveTelegramChannels() {
        RemoveTelegramChannelsFragment.newInstance()
            .open(activity, R.id.vgContent, RemoveTelegramChannelsFragment.TAG)
    }

    override fun onBackPressed() {
        hideApp()
    }

}