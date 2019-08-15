package deniskaminskiy.paperboy.presentation.view.post

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import deniskaminskiy.paperboy.R

class PostTextView(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    init {
        inflate(context, R.layout.view_post_text, null)
    }

}