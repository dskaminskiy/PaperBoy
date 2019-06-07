package deniskaminskiy.paperboy.presentation.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.presentation.view.CheckItemView
import deniskaminskiy.paperboy.presentation.view.DividerView
import deniskaminskiy.paperboy.utils.OnItemClick

class SuperAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CHECK_ITEM = 1
        private const val VIEW_TYPE_MIDDLE_ITEM = 1
    }

    var onItemClick: OnItemClick<SuperItemPresentItemModel<*>> = {}

    private val data = mutableListOf<SuperItemPresentItemModel<*>>()

    fun setData(data: List<SuperItemPresentItemModel<*>>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_CHECK_ITEM -> CheckItemViewHolder(parent.context)
            // divider
            else -> DividerItemViewHolder(parent.context)
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when {
            holder is CheckItemViewHolder && item is CheckItemPresentItemModel<*> ->
                holder.show(item)
        }
    }

    private inner class CheckItemViewHolder(
        context: Context,
        private val checkItemView: CheckItemView = CheckItemView(context)
    ) : RecyclerView.ViewHolder(checkItemView) {

        init {
            checkItemView.setOnClickListener {
                data.getOrNull(adapterPosition)
                    ?.let {
                        onItemClick.invoke(it)
                    }
            }
        }

        fun show(itemModel: CheckItemPresentItemModel<*>) {
            checkItemView.show(itemModel.model)
        }

    }

    private inner class DividerItemViewHolder(
        context: Context,
        dividerView: View = DividerView(context)
    ) : RecyclerView.ViewHolder(dividerView)


}

//TODO: попробуй дома домыслить
interface SuperItemChildPresentModel

sealed class SuperItemPresentItemModel{

    inline fun <reified T> ifTypeOf(func: (T) -> Unit): SuperItemPresentItemModel {

    }

    private inline fun <reified T> checkOnTypeOrNull(): T? {

    }

    // inline fun retrieveElement(func) {}

   /* inline fun <reified T> ifCheckItem(ifCheckItem: (T) -> Unit): SuperItemPresentModel {
        checkItemOrNull<T>()?.let(ifCheckItem)
        return this
    }

    inline fun <reified T> checkItemOrNull(): T? {
        val s = (this as? CheckItemPresentItemModel<*>)
        return (s?.element as? T)
    }*/

}

class CheckItemPresentItemModel<out T>(
    val element: T,
    val model: CheckItemPresentModel
) : SuperItemPresentItemModel()

/*data class MiddleItemPresentItemModel<out T>(
    val element: T,
    val model: MiddleItemPresentModel
)*/

object DividerPresentItemModel : SuperItemPresentItemModel<Unit>(Unit)