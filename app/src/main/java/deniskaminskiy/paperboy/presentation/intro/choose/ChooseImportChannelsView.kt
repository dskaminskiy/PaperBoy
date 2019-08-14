package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.presentation.base.CheckItemPresentItemModel

interface ChooseImportChannelsView : View {

    fun show(model: ChooseImportChannelsPresModel)

    fun showLoading()

    fun hideLoading()

    fun hideFab()

    fun showRemoveTelegramChannels()

}

data class ChooseImportChannelsPresModel(
    val title: SpannableStringBuilder,
    val subtitle: String,
    val channels: List<CheckItemPresentItemModel<ImportChannel>>,
    val isFabVisible: Boolean
)