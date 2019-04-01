package deniskaminskiy.paperboy.utils

import androidx.recyclerview.widget.RecyclerView

abstract class DataAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val data = mutableListOf<T>()

    fun setData(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

}