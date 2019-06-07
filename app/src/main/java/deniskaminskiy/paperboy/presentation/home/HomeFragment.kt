package deniskaminskiy.paperboy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.utils.compatFont
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomePresenter, HomeView>(), HomeView {

    companion object {

        const val TAG = "HomeFragment"

        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vCollapsingToolbar.apply {
            val titleTypeface = context.compatFont(R.font.ibm_plex_serif_bold)
            setCollapsedTitleTypeface(titleTypeface)
            setExpandedTitleTypeface(titleTypeface)
        }
    }

}