package deniskaminskiy.paperboy.utils.view

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ViewTextDelegate(
    private val textViewId: Int,
    private val isGoneIfBlank: Boolean = true
) : ReadWriteProperty<ViewGroup, String> {

    private var textView: TextView? = null

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): String =
        textView(thisRef).text.toString()


    override fun setValue(thisRef: ViewGroup, property: KProperty<*>, value: String) {
        textView(thisRef) goneIf (value.isBlank() && isGoneIfBlank)
        textView(thisRef).text = value
    }

    private fun textView(layout: ViewGroup): TextView =
            textView.let {
                if (it == null) {
                    textView = layout.findViewById(textViewId)
                    textView!!
                } else it
            }

}

class ViewTextColorDelegate(
    private val textViewId: Int,
    @ColorInt
    private val defaultColor: Int? = null
) : ReadWriteProperty<ViewGroup, Int> {

    private var textView: TextView? = null

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): Int {
        return textView(thisRef).currentTextColor
    }

    override fun setValue(thisRef: ViewGroup, property: KProperty<*>, @ColorInt value: Int) {
        if (value != -1) {
            textView(thisRef).setTextColor(value)
        } else defaultColor?.let(textView(thisRef)::setTextColor)
    }

    private fun textView(layout: ViewGroup): TextView =
        textView.let {
            if (it == null) {
                textView = layout.findViewById(textViewId)
                textView!!
            } else it
        }
}

class ViewVisibilityDelegate(
    private val viewId: Int,
    private val notVisibleState: ViewVisibilityState = ViewVisibilityState.GONE
) : ReadWriteProperty<ViewGroup, Boolean> {

    private var view: View? = null

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): Boolean =
        view(thisRef).isVisible

    override fun setValue(thisRef: ViewGroup, property: KProperty<*>, value: Boolean) {
        view(thisRef).visibility = if (value) View.VISIBLE else notVisibleState.stateId
    }

    private fun view(layout: ViewGroup): View =
        view.let {
            if (it == null) {
                view = layout.findViewById(viewId)
                view!!
            } else it
        }

}

enum class ViewVisibilityState(val stateId: Int) {
    VISIBLE(View.VISIBLE),
    INVISIBLE(View.INVISIBLE),
    GONE(View.GONE)
}