package deniskaminskiy.paperboy.presentation.intro.choose

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import deniskaminskiy.paperboy.presentation.view.CheckItemPresentModel
import deniskaminskiy.paperboy.presentation.view.CheckItemView
import deniskaminskiy.paperboy.utils.DataAdapter
import deniskaminskiy.paperboy.utils.OnItemClick

class CheckItemAdapter : DataAdapter<CheckItemPresentItemModel<*>, CheckItemAdapter.ViewHolder>() {

    var onItemClick: OnItemClick<CheckItemPresentModel> = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.show(data[position])
    }

    inner class ViewHolder(
        context: Context,
        private val checkItemView: CheckItemView = CheckItemView(context)
    ) : RecyclerView.ViewHolder(checkItemView) {

        init {
            checkItemView.setOnClickListener {
                data.getOrNull(adapterPosition)
                    ?.model
                    ?.let(onItemClick)
            }
        }

        fun show(itemModel: CheckItemPresentItemModel<*>) {
            checkItemView.show(itemModel.model)
        }

    }

}

data class CheckItemPresentItemModel<out T>(
    val element: T,
    val model: CheckItemPresentModel
)