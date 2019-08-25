package deniskaminskiy.paperboy.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class DataAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val data = mutableListOf<T>()

    var onItemClick: OnItemClick<T> = {}

    override fun getItemCount(): Int = data.size

    fun setData(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    protected fun View.setOnItemClickListener(adapterPosition: Int) {
        this.setOnClickListener {
            data.getOrNull(adapterPosition)
                ?.let {
                    onItemClick.invoke(it)
                }
        }
    }

}