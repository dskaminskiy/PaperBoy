package deniskaminskiy.paperboy.presentation.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.base.SuperAdapter
import deniskaminskiy.paperboy.presentation.base.SuperItemPresentItemModel
import deniskaminskiy.paperboy.presentation.view.SwipeRefreshPrintingHeader
import deniskaminskiy.paperboy.utils.OutlineProviderFactory
import deniskaminskiy.paperboy.utils.hideApp
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import deniskaminskiy.paperboy.utils.view.visible
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomePresenter, HomeView>(), HomeView {

    companion object {

        const val TAG = "HomeFragment"

        fun newInstance() = HomeFragment()
    }

    private val adapter = SuperAdapter()
    private var isContentScrollable = true

    private val appBarOffsetListener = AppBarLayout.OnOffsetChangedListener { _, offset ->
        // Необходимо для правильной координации swipeLayout'a и CollapsingToolbar'a
        vSwipeRefresh.isEnabled = offset == 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCustomSwipeRefreshLayout()

        rvChannels.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return isContentScrollable && super.canScrollVertically()
            }
        }
        rvChannels.itemAnimator = DefaultItemAnimator()
        rvChannels.adapter = adapter

        vAppBar.outlineProvider = OutlineProviderFactory.outlineProviderDefault

        presenter = HomePresenter(this, AndroidResourcesManager.create(this))
            .apply {
                vSwipeRefresh.setOnRefreshListener { onRefresh() }
            }

    }

    override fun onResume() {
        super.onResume()
        vAppBar.addOnOffsetChangedListener(appBarOffsetListener)
    }

    override fun onPause() {
        super.onPause()
        vAppBar.removeOnOffsetChangedListener(appBarOffsetListener)
    }

    private fun initCustomSwipeRefreshLayout() {
        vSwipeRefresh.apply {
            setCustomHeadview(SwipeRefreshPrintingHeader(requireContext()))
            triggerDistance = 32
        }
    }

    override fun showTitleTypeface(font: Typeface) {
        vCollapsingToolbar.apply {
            setCollapsedTitleTypeface(font)
            setExpandedTitleTypeface(font)
        }
    }

    override fun show(items: List<SuperItemPresentItemModel>) {
        if (vSwipeRefresh.isRefreshing) vSwipeRefresh.refreshComplete()
        vProgress.visibility = View.GONE
        adapter.setData(items)
    }

    override fun showLoading() {
        vProgress.visible()
    }

    override fun enableScrolling() {
        isContentScrollable = true
    }

    override fun disableScrolling() {
        isContentScrollable = false
    }

    override fun onBackPressed() {
        hideApp()
    }

}