package deniskaminskiy.paperboy.utils.view

import android.view.View

fun View.gone() {
    visibility = View.GONE
}

val View.isGone: Boolean
    get() = visibility == View.GONE

val View.isVisible: Boolean
    get() = visibility == View.VISIBLE

val View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE

fun View.visible() {
    visibility = View.VISIBLE
}

infix fun View.goneIf(expr: Boolean) {
    visibility = if (expr) View.GONE else View.VISIBLE
}

infix fun View.invisibleIf(expr: Boolean) {
    visibility = if (expr) View.INVISIBLE else View.VISIBLE
}