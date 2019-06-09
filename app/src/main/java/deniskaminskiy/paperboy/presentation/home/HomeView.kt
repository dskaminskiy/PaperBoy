package deniskaminskiy.paperboy.presentation.home

import android.graphics.Typeface
import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.presentation.base.SuperItemPresentItemModel

interface HomeView : View {

    fun showTitleTypeface(font: Typeface)

    fun show(items: List<SuperItemPresentItemModel>)

    fun showLoading()

}