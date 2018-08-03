package thaihn.com.weatherapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import thaihn.com.weatherapp.data.model.DataDaily
import thaihn.com.weatherapp.data.model.DataHourly
import thaihn.com.weatherapp.data.model.Info
import thaihn.com.weatherapp.screen.main.adapter.HourlyAdapter
import thaihn.com.weatherapp.screen.main.adapter.InfoAdapter
import thaihn.com.weatherapp.screen.main.adapter.WeeklyAdapter

object BindingUtil {

    @BindingAdapter("infoItem")
    @JvmStatic
    fun setAdapterHourly(recyclerView: RecyclerView, items: List<Info>) {
        val adapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (adapter != null && adapter is InfoAdapter) {
            if (items != null) {
                adapter.setNewData(items)
            }
        }
    }

    @BindingAdapter("hourlyItem")
    @JvmStatic
    fun setItemHourly(recyclerView: RecyclerView, items: List<DataHourly>?) {
        val adapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (adapter != null && adapter is HourlyAdapter) {
            if (items != null) {
                adapter.setNewData(items)
            }
        }
    }

    @BindingAdapter("weeklyItem")
    @JvmStatic
    fun setItemWeekly(recyclerView: RecyclerView, items: List<DataDaily>?) {
        val adapter: RecyclerView.Adapter<*>? = recyclerView.adapter
        if (adapter != null && adapter is WeeklyAdapter) {
            if (items != null) {
                adapter.setNewData(items)
            }
        }
    }

    @BindingAdapter("imageWeekly")
    @JvmStatic
    fun loadImageWeekly(imageView: ImageView, icon: String) {
        Glide.with(imageView.context).load(
                Utils.getImage(Utils.changeIconToName(icon), imageView.context)).into(
                imageView)
    }

    @BindingAdapter("imageHourly")
    @JvmStatic
    fun loadImageHourly(imageView: ImageView, icon: String) {
        Glide.with(imageView.context).load(
                Utils.getImage(Utils.changeIconToName(icon), imageView.context)).into(
                imageView)
    }
}
