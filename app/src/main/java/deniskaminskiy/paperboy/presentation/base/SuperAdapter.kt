package deniskaminskiy.paperboy.presentation.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deniskaminskiy.paperboy.core.Mapper
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.presentation.view.CheckItemView
import deniskaminskiy.paperboy.presentation.view.MiddleItemPresentModel
import deniskaminskiy.paperboy.presentation.view.MiddleItemView
import deniskaminskiy.paperboy.utils.OnItemClick
import deniskaminskiy.paperboy.utils.dp
import deniskaminskiy.paperboy.utils.managers.AndroidResourcesManager

class SuperAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CHECK_ITEM = 1
        private const val VIEW_TYPE_MIDDLE_ITEM = 2
        private const val VIEW_TYPE_DIVIDER = 3
    }

    var onItemClick: OnItemClick<SuperItemPresentItemModel> = {}

    private val data = mutableListOf<SuperItemPresentItemModel>()

    fun setData(data: List<SuperItemPresentItemModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_CHECK_ITEM -> CheckItemViewHolder(parent.context)
            VIEW_TYPE_MIDDLE_ITEM -> MiddleItemViewHolder(parent.context)
            // divider
            else -> DividerItemViewHolder(parent.context)
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when {
            holder is CheckItemViewHolder && item is CheckItemPresentItemModel<*> ->
                holder.show(item)
            holder is MiddleItemViewHolder && item is MiddleItemPresentItemModel<*> ->
                holder.show(item)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is MiddleItemPresentItemModel<*> -> VIEW_TYPE_MIDDLE_ITEM
            is CheckItemPresentItemModel<*> -> VIEW_TYPE_CHECK_ITEM
            else -> VIEW_TYPE_DIVIDER
        }

    private inner class CheckItemViewHolder(
        context: Context,
        private val checkItemView: CheckItemView = CheckItemView(context)
    ) : RecyclerView.ViewHolder(checkItemView) {

        init {
            checkItemView.setOnClickListener { onItemClick(adapterPosition) }
        }

        fun show(itemModel: CheckItemPresentItemModel<*>) {
            checkItemView.show(itemModel.model)
        }

    }

    private inner class MiddleItemViewHolder(
        context: Context,
        private val middleItemView: MiddleItemView = MiddleItemView(context)
    ) : RecyclerView.ViewHolder(middleItemView) {

        init {
            middleItemView.setOnClickListener { onItemClick(adapterPosition) }
        }

        fun show(itemModel: MiddleItemPresentItemModel<*>) {
            middleItemView.show(itemModel.model)
        }

    }

    private fun onItemClick(adapterPosition: Int) {
        data.getOrNull(adapterPosition)
            ?.let {
                onItemClick.invoke(it)
            }
    }

    private inner class DividerItemViewHolder(
        context: Context,
        dividerView: View = View(context).apply {
            setBackgroundColor(AndroidResourcesManager.create(context).colors.print20)
            layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 1.dp(context))
                .apply {
                    bottomMargin = 8.dp(context)
                }
        }
    ) : RecyclerView.ViewHolder(dividerView)


}

sealed class SuperItemPresentItemModel(
    val element: Any
) {

    inline fun <reified T> ifTypeOf(func: (T) -> Unit): SuperItemPresentItemModel {
        (element as? T)?.let(func)
        return this
    }

}

class CheckItemPresentItemModel<out T : Any>(
    element: T,
    val model: CheckItemPresentModel
) : SuperItemPresentItemModel(element)

class MiddleItemPresentItemModel<out T : Any>(
    element: T,
    val model: MiddleItemPresentModel
) : SuperItemPresentItemModel(element)

object DividerPresentItemModel : SuperItemPresentItemModel(Unit)


/**
 * Данный маппер обеспечивает лишь обязательное реализацию "под-маппера" из бизнес-объекта в [CheckItemPresentModel].
 */
class CheckItemToSuperItemPresentItemModelMapper<T : Any>(
    private val modelToPresentModelMapper: Mapper<T, CheckItemPresentModel>
) : Mapper<List<T>, List<SuperItemPresentItemModel>> {

    override fun map(from: List<T>): List<SuperItemPresentItemModel> =
        from.map { CheckItemPresentItemModel(it, modelToPresentModelMapper.map(it)) }

}

/**
 * Данный маппер обеспечивает лишь обязательную реализацию "под-маппера" из бизнес-объекта в [MiddleItemPresentModel].
 */
class MiddleItemToSuperItemPresentItemModelMapper<T : Any>(
    private val modelToPresentModelMapper: Mapper<T, MiddleItemPresentModel>
) : Mapper<List<T>, List<SuperItemPresentItemModel>> {

    override fun map(from: List<T>): List<SuperItemPresentItemModel> =
        from.map { MiddleItemPresentItemModel(it, modelToPresentModelMapper.map(it)) }

}