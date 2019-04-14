package deniskaminskiy.paperboy.utils

import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.annotation.ColorInt

object TextUtils {

    fun paintString(text: String, @ColorInt color: Int): Spanned =
        if (text.isBlank()) {
            SpannableString(text)
        } else {
            Html.fromHtml("<font color='${ColorUtils.hexColor(color)}'>$text</font>")
        }

}

/**
 * Красит найденное слово в предложении в указанный цвет.
 *
 * @return - покрашенную версию предложения или ту же версию строки в случае не корректного выполнения поиска слова.
 */
fun String.paintWord(word: String, @ColorInt color: Int): SpannableStringBuilder {

    val result = SpannableStringBuilder()

    val beforeWord = substringBefore(word)
    if (beforeWord == this) return result.append(this)

    val afterWord = substringAfter(word)
    if (afterWord == this) return result.append(this)

    result.append(beforeWord, TextUtils.paintString(word, color), afterWord)

    return result
}