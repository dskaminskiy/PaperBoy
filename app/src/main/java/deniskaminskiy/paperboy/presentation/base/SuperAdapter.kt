package deniskaminskiy.paperboy.presentation.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deniskaminskiy.paperboy.R
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.presentation.view.CheckItemPresModel
import deniskaminskiy.paperboy.presentation.view.CheckItemView
import deniskaminskiy.paperboy.presentation.view.MiddleItemPresModel
import deniskaminskiy.paperboy.presentation.view.MiddleItemView
import deniskaminskiy.paperboy.utils.DataAdapter
import deniskaminskiy.paperboy.utils.compatColor
import deniskaminskiy.paperboy.utils.dp

class SuperAdapter : DataAdapter<SuperItemPresItemModel, RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CHECK_ITEM = 1
        private const val VIEW_TYPE_MIDDLE_ITEM = 2
        private const val VIEW_TYPE_DIVIDER = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_CHECK_ITEM -> CheckItemViewHolder(parent.context)
            VIEW_TYPE_MIDDLE_ITEM -> MiddleItemViewHolder(parent.context)
            else -> DividerItemViewHolder(parent.context)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when {
            holder is CheckItemViewHolder && item is CheckItemPresItemModel<*> ->
                holder.show(item)
            holder is MiddleItemViewHolder && item is MiddleItemPresItemModel<*> ->
                holder.show(item)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is MiddleItemPresItemModel<*> -> VIEW_TYPE_MIDDLE_ITEM
            is CheckItemPresItemModel<*> -> VIEW_TYPE_CHECK_ITEM
            else -> VIEW_TYPE_DIVIDER
        }

    private inner class CheckItemViewHolder(
        context: Context,
        private val checkItemView: CheckItemView = CheckItemView(context)
    ) : RecyclerView.ViewHolder(checkItemView) {

        init {
            checkItemView.setOnItemClickListener(adapterPosition)
        }

        fun show(itemModel: CheckItemPresItemModel<*>) {
            checkItemView.show(itemModel.model)
        }

    }

    private inner class MiddleItemViewHolder(
        context: Context,
        private val middleItemView: MiddleItemView = MiddleItemView(context)
    ) : RecyclerView.ViewHolder(middleItemView) {

        init {
            middleItemView.setOnItemClickListener(adapterPosition)
        }

        fun show(itemModel: MiddleItemPresItemModel<*>) {
            middleItemView.show(itemModel.model)
        }

    }

    private inner class DividerItemViewHolder(
        context: Context,
        dividerView: View = View(context).apply {
            setBackgroundColor(context.compatColor(R.color.print20))
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 1.dp(context))
                .apply {
                    bottomMargin = 8.dp(context)
                }
        }
    ) : RecyclerView.ViewHolder(dividerView)


}

sealed class SuperItemPresItemModel(
    val element: Any
) {

    inline fun <reified T> ifTypeOf(func: (T) -> Unit): SuperItemPresItemModel {
        (element as? T)?.let(func)
        return this
    }

}

class CheckItemPresItemModel<out T : Any>(
    element: T,
    val model: CheckItemPresModel
) : SuperItemPresItemModel(element)

class MiddleItemPresItemModel<out T : Any>(
    element: T,
    val model: MiddleItemPresModel
) : SuperItemPresItemModel(element)

object DividerPresItemModel : SuperItemPresItemModel(Unit)


/**
 * Данный маппер обеспечивает лишь обязательное реализацию "под-маппера" из бизнес-объекта в [CheckItemPresModel].
 */
class CheckItemToSuperItemPresentItemModelMapper<T : Any>(
    private val modelToPresModelMapper: Mapper<T, CheckItemPresModel>
) : Mapper<List<T>, List<SuperItemPresItemModel>> {

    override fun map(from: List<T>): List<SuperItemPresItemModel> =
        from.map { CheckItemPresItemModel(it, modelToPresModelMapper.map(it)) }

}

/**
 * Данный маппер обеспечивает лишь обязательную реализацию "под-маппера" из бизнес-объекта в [MiddleItemPresModel].
 */
class MiddleItemToSuperItemPresentItemModelMapper<T : Any>(
    private val modelToPresModelMapper: Mapper<T, MiddleItemPresModel>
) : Mapper<List<T>, List<SuperItemPresItemModel>> {

    override fun map(from: List<T>): List<SuperItemPresItemModel> =
        from.map { MiddleItemPresItemModel(it, modelToPresModelMapper.map(it)) }

}