package deniskaminskiy.paperboy.presentation.view.post

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import deniskaminskiy.paperboy.R

class PostHeaderView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object {

    }


    init {
        inflate(context, R.layout.view_post_header, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.PostHeaderView, 0, 0)

        //. get custom attrs

        a.recycle()
    }

}