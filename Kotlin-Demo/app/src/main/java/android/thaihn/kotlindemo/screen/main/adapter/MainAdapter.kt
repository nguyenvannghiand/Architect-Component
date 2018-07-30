package android.thaihn.kotlindemo.screen.main.adapter

import android.content.Context
import android.thaihn.kotlindemo.R
import android.thaihn.kotlindemo.data.model.Weather
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_weather.view.*

class MainAdapter(var items: List<Weather>, var context: Context, var listener: OnListenerMain)
  : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false)
        , listener)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binData(items[position])
  }


  class ViewHolder(view: View, var click: OnListenerMain) : RecyclerView.ViewHolder(view),
      View.OnClickListener {

    val textName = view.text_name!!
    val image = view.image!!
    val textTemp = view.text_temp!!

    init {
      itemView.setOnClickListener(this)
    }

    fun binData(weather: Weather) {
      textName.text = weather.name
      image.setImageResource(R.drawable.ic_sun)
      if (weather.main == null) {
        textTemp.text = "30*C"
      } else {
        textTemp.text = transformKtoC(weather.main?.temp)
      }
    }

    private fun transformKtoC(inputK: Double?): String {
      val result = inputK!! - 273.5
      return String.format("%.2f", result) + "*C"
    }

    override fun onClick(v: View?) {
      if (v == itemView) {
        click.onClickItem(adapterPosition)
      }
    }
  }

  fun setWeathers(weathers: List<Weather>) {
    this.items = weathers
  }

  fun getWeathers(): List<Weather> {
    return this.items
  }
}

interface OnListenerMain {

  fun onClickItem(id: Int)
}