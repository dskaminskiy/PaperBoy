package deniskaminskiy.paperboy.utils

import android.content.res.TypedArray
import deniskaminskiy.paperboy.presentation.view.data.ItemConstantIconPresModel
import deniskaminskiy.paperboy.utils.icon.IconAttrs
import deniskaminskiy.paperboy.utils.icon.IconFactory

fun TypedArray.getIcon(iconAttrs: IconAttrs): ItemConstantIconPresModel =
    ItemConstantIconPresModel(
        icon = getInt(iconAttrs.constant, -1)
            .takeIf { it != -1 }?.let(IconFactory::create),
        iconColor = iconAttrs.color?.let { getInt(it, -1) } ?: -1,
        backgroundColor = iconAttrs.backgroundColor?.let { getInt(it, -1) } ?: -1
    )