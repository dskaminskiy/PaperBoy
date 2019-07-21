package deniskaminskiy.paperboy.presentation.intro.remove

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.View

interface RemoveTelegramChannelsView : View {

    fun show(model: RemoveTelegramChannelsPresentModel)

    fun showHome()

}

data class RemoveTelegramChannelsPresentModel(
    val title: SpannableStringBuilder
)
