package thaihn.com.weatherapp.screen.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thaihn.com.weatherapp.BR
import thaihn.com.weatherapp.data.model.Info
import thaihn.com.weatherapp.databinding.ItemInfoBinding

class InfoAdapter(
        var items: List<Info>
) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(items[position])
    }

    class ViewHolder(private var binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binData(item: Info) {
            binding.apply {
                setVariable(BR.info, item)
                executePendingBindings()
            }
        }
    }

    fun setNewData(newItems: List<Info>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}
