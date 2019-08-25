package deniskaminskiy.paperboy.presentation.home

import android.graphics.Typeface
import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.presentation.base.SuperItemPresItemModel

interface HomeView : View {

    fun showTitleTypeface(font: Typeface)

    fun show(items: List<SuperItemPresItemModel>)

    fun disableScrolling()

    fun enableScrolling()

    fun showLoading()

}