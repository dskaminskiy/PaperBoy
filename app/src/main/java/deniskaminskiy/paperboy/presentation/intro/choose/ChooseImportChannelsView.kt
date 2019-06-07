package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.data.importchannels.ImportChannel
import deniskaminskiy.paperboy.presentation.base.CheckItemPresentItemModel

interface ChooseImportChannelsView : View {

    fun show(model: ChooseImportChannelsPresentModel)

    fun showLoading()

    fun hideLoading()

    fun showRemoveTelegramChannels()

}

data class ChooseImportChannelsPresentModel(
    val title: SpannableStringBuilder,
    val subtitle: String,
    val channels: List<CheckItemPresentItemModel<ImportChannel>>,
    val isFabVisible: Boolean
)