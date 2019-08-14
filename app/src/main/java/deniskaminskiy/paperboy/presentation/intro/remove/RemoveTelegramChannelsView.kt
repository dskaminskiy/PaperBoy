package deniskaminskiy.paperboy.presentation.intro.remove

import android.text.SpannableStringBuilder
import deniskaminskiy.paperboy.core.View

interface RemoveTelegramChannelsView : View {

    fun show(model: RemoveTelegramChannelsPresModel)

    fun showHome()

}

data class RemoveTelegramChannelsPresModel(
    val title: SpannableStringBuilder
)
