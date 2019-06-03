package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.data.importchannels.ImportChannel

interface ChooseImportChannelsView : View {

    fun show(model: ChooseImportChannelsPresentModel)

    fun showLoading()

    fun showRemoveTelegramChannels()

}

data class ChooseImportChannelsPresentModel(
    val title: SpannableStringBuilder,
    val subtitle: String,
    val channels: List<CheckItemPresentItemModel<ImportChannel>>,
    val isFabVisible: Boolean
)