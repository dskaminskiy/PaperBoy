package deniskaminskiy.paperboy.utils.view

import android.widget.EditText
import deniskaminskiy.paperboy.utils.SimpleTextWatcher

inline fun EditText.addOnTextChangedListener(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            listener(s.toString())
        }
    })
}