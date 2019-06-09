package deniskaminskiy.paperboy.presentation.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deniskaminskiy.paperboy.presentation.view.*
import deniskaminskiy.paperboy.utils.OnItemClick

class SuperAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CHECK_ITEM = 1
        private const val VIEW_TYPE_MIDDLE_ITEM = 2
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
        dividerView: View = DividerView(context)
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