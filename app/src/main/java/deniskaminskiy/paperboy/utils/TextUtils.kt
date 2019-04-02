package deniskaminskiy.paperboy.utils

import android.text.Html
import android.text.SpannableString
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