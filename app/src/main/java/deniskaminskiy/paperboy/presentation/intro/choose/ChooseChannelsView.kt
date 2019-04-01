package deniskaminskiy.paperboy.presentation.intro.choose

import deniskaminskiy.paperboy.core.View
import deniskaminskiy.paperboy.data.channel.ChannelImport

interface ChooseChannelsView : View {

    fun show(model: ChooseChannelsPresentModel)

}

data class ChooseChannelsPresentModel(
    val title: String,
    val subtitle: String,
    val channels: List<CheckItemPresentItemModel<ChannelImport>>
)