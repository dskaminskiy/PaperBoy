package deniskaminskiy.paperboy.presentation.feed.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.presentation.view.post.PostHeaderPresModel
import deniskaminskiy.paperboy.presentation.view.post.PostHeaderView
import deniskaminskiy.paperboy.presentation.view.post.PostImageView
import deniskaminskiy.paperboy.presentation.view.post.PostTextView
import deniskaminskiy.paperboy.utils.DataAdapter
import deniskaminskiy.paperboy.utils.compatColor
import deniskaminskiy.paperboy.utils.dp

class PostAdapter : DataAdapter<PostPresItemModel, RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_DIVIDER = 2
        private const val VIEW_TYPE_IMAGE = 3
        private const val VIEW_TYPE_TEXT = 4

        private const val MARGIN_START_DP = 16
        private const val MARGIN_END_DP = 16
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_HEADER -> PostHeaderViewHolder(parent.context)
            VIEW_TYPE_IMAGE -> PostImageViewHolder(parent.context)
            VIEW_TYPE_TEXT -> PostTextViewHolder(parent.context)
            else -> PostDividerViewHolder(parent.context)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when {
            holder is PostHeaderViewHolder && item is PostHeaderPresItemModel<*> ->
                holder.show(item)
            holder is PostImageViewHolder && item is PostImagePresItemModel<*> ->
                holder.show(item)
            holder is PostTextViewHolder && item is PostTextPresItemModel<*> ->
                holder.show(item)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is PostHeaderPresItemModel<*> -> VIEW_TYPE_HEADER
            is PostImagePresItemModel<*> -> VIEW_TYPE_IMAGE
            is PostTextPresItemModel<*> -> VIEW_TYPE_TEXT
            else -> VIEW_TYPE_DIVIDER
        }

    private inner class PostHeaderViewHolder(
        context: Context,
        private val postHeaderView: PostHeaderView = PostHeaderView(context)
    ) : RecyclerView.ViewHolder(postHeaderView) {

        init {
            postHeaderView.setOnItemClickListener(adapterPosition)
        }

        fun show(itemModel: PostHeaderPresItemModel<*>) {
            postHeaderView.show(itemModel.model)
        }

    }

    private inner class PostDividerViewHolder(
        context: Context,
        dividerView: View = View(context).apply {
            setBackgroundColor(context.compatColor(R.color.print20))
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 1.dp(context))
                .apply {
                    marginStart = MARGIN_START_DP.dp(context)
                }
        }
    ) : RecyclerView.ViewHolder(dividerView)

    private inner class PostImageViewHolder(
        context: Context,
        private val postImageView: PostImageView = PostImageView(context)
    ) : RecyclerView.ViewHolder(postImageView) {

        init {
            postImageView.setOnItemClickListener(adapterPosition)
        }

        fun show(model: PostImagePresItemModel<*>) {
            postImageView.show(model.url)
        }

    }

    private inner class PostTextViewHolder(
        context: Context,
        private val postTextView: PostTextView = PostTextView(context).applyMargins()
    ) : RecyclerView.ViewHolder(postTextView) {

        init {
            postTextView.setOnItemClickListener(adapterPosition)
        }

        fun show(model: PostTextPresItemModel<*>) {
            postTextView.text = model.text
        }

    }

    private fun <T : View> T.applyMargins(): T =
        this.apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
                .apply {
                    marginStart = MARGIN_START_DP.dp(context)
                    marginEnd = MARGIN_END_DP.dp(context)
                }
        }

}

sealed class PostPresItemModel(
    val element: Any
) {

    inline fun <reified T> ifTypeOf(func: (T) -> Unit): PostPresItemModel {
        (element as? T)?.let(func)
        return this
    }

}

class PostHeaderPresItemModel<out T : Any>(
    element: T,
    val model: PostHeaderPresModel
) : PostPresItemModel(element)

object PostDividerPresItemModel : PostPresItemModel(Unit)

class PostImagePresItemModel<out T : Any>(
    element: T,
    val url: String
) : PostPresItemModel(element)

class PostTextPresItemModel<out T : Any>(
    element: T,
    val text: String
) : PostPresItemModel(element)