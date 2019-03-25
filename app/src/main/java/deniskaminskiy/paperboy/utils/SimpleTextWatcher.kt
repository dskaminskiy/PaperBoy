package deniskaminskiy.paperboy.utils

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // Пустая реализация
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        // Пустая реализация
    }

    override fun afterTextChanged(s: Editable) {
        // Пустая реализация
    }

}