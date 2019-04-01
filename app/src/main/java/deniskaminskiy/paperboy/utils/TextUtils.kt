package deniskaminskiy.paperboy.utils

import androidx.annotation.ColorInt

object TextUtils {

    fun colorLastWord(sentence: String, @ColorInt color: Int): String {
        if (sentence.isBlank()) return sentence
        if (!sentence.contains(" ")) return sentence // with color ofc

        val lastWord = sentence.substring(sentence.lastIndexOf(" ") + 1)
        val otherSentence = sentence.dropLast(lastWord.length)

        //TODO: Сюда нужен hex крч
        val coloredLastWord = "<font color='$color.h'>$lastWord</font>"

        return  coloredLastWord
    }

}