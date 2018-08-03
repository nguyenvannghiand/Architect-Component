package thaihn.com.weatherapp.screen.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thaihn.com.weatherapp.data.model.DataHourly
import thaihn.com.weatherapp.databinding.ItemHourlyBinding
import thaihn.com.weatherapp.util.Utils

class HourlyAdapter(
        var items: List<DataHourly>
) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(items[position])
    }

    class ViewHolder(var binding: ItemHourlyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binData(hourly: DataHourly) {
            binding.apply {
                time = Utils.convertTimeToHour(hourly.time)
                image = hourly.icon
                temp = Utils.changeTempFToC(hourly.temperature).toString() + Utils.makeTemp()
                executePendingBindings()
            }
        }
    }

    fun setNewData(newItems: List<DataHourly>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}
