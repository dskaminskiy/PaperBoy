package deniskaminskiy.paperboy.utils

import android.content.res.TypedArray
import deniskaminskiy.paperboy.presentation.view.data.ItemConstantIconPresModel
import deniskaminskiy.paperboy.utils.icon.IconAttrs
import deniskaminskiy.paperboy.utils.icon.IconFactory

fun TypedArray.getIcon(iconAttrs: IconAttrs): ItemConstantIconPresModel =
    ItemConstantIconPresModel(
        icon = getInt(iconAttrs.constant, -1)
            .takeIf { it != -1 }?.let(IconFactory::create),
        iconColor = getInt(iconAttrs.color, -1),
        backgroundColor = getInt(iconAttrs.backgroundColor, -1)
    )