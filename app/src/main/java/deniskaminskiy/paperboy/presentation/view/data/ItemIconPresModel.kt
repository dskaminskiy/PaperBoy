package deniskaminskiy.paperboy.presentation.view.data

import androidx.annotation.ColorInt
import deniskaminskiy.paperboy.utils.icon.ConstantIcon

sealed class ItemIconPresModel

object ItemDefaultIconPresModel : ItemIconPresModel()

data class ItemConstantIconPresModel(
    val icon: ConstantIcon? = null,
    @ColorInt val iconColor: Int = -1,
    @ColorInt val backgroundColor: Int = -1
) : ItemIconPresModel()

data class ItemUrlIconPresModel(
    val url: String
) : ItemIconPresModel()