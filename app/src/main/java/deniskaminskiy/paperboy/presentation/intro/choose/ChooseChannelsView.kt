package deniskaminskiy.paperboy.presentation.intro.choose

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.data.channel.ChannelImport

interface ChooseChannelsView : View {

    fun show(model: ChooseChannelsPresentModel)

    fun showUnknownError()

    fun showRemoveTelegramChannels()

}

data class ChooseChannelsPresentModel(
    val title: SpannableStringBuilder,
    val subtitle: String,
    val channels: List<CheckItemPresentItemModel<ChannelImport>>
)