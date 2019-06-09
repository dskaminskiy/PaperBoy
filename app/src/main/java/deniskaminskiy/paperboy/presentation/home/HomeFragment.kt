package deniskaminskiy.paperboy.presentation.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.BaseFragment
import deniskaminskiy.paperboy.presentation.base.SuperAdapter
import deniskaminskiy.paperboy.presentation.base.SuperItemPresentItemModel
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomePresenter, HomeView>(), HomeView {

    companion object {

        const val TAG = "HomeFragment"

        fun newInstance() = HomeFragment()
    }

    private val adapter = SuperAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = HomePresenter(this, AndroidResourcesManager.create(this))

        rvChannels.layoutManager = LinearLayoutManager(context)
        rvChannels.adapter = adapter

    }

    override fun showTitleTypeface(font: Typeface) {
        vCollapsingToolbar.apply {
            setCollapsedTitleTypeface(font)
            setExpandedTitleTypeface(font)
        }
    }

    override fun show(items: List<SuperItemPresentItemModel>) {
        vProgress.hide()
        adapter.setData(items)
    }

    override fun showLoading() {
        vProgress.show()
    }

}