package android.thaihn.kotlindemo.screen.main

import android.content.Context
import android.content.Intent
import android.thaihn.kotlindemo.MyApplication
import android.thaihn.kotlindemo.R
import android.thaihn.kotlindemo.data.model.Main
import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.data.room.dao.WeatherDao
import android.thaihn.kotlindemo.screen.main.adapter.MainAdapter
import android.thaihn.kotlindemo.screen.main.adapter.OnListenerMain
import android.thaihn.kotlindemo.screen.search.SearchActivity
import android.thaihn.kotlindemo.utils.ToastUtil
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.thaihn.kotlinstart.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener, OnListenerMain {

  private var weatherDao: WeatherDao? = null
  private var adapter = MainAdapter(ArrayList<Weather>(), this, this)

  override fun getLayoutResource(): Int {
    return R.layout.activity_main
  }

  override fun initVariable() {
    fab_add.setOnClickListener(this)
    fab_next.setOnClickListener(this)
  }

  override fun initData() {
    weatherDao = MyApplication.database?.getWeatherDao()
    configRecycleView(this)
    listenerWeather()
    title = "Room and Livedata"
    tryRxOnKotlin()
  }

  override fun onClick(view: View?) {
    when (view?.id) {
      R.id.fab_add -> {
        addWeather()
      }
      R.id.fab_next -> {
        var intent = SearchActivity.newIntent(this, "")
        startActivity(intent)
      }
    }
  }

  override fun onClickItem(id: Int) {
    ToastUtil.quickToast(this, "Click " + id)
  }

  fun listenerWeather() {
    weatherDao?.getAllWeather()?.observe(this,
        androidx.lifecycle.Observer { t ->
          update(t)
        })
  }

  fun update(items: List<Weather>) {
    adapter.setWeathers(items)
    adapter.notifyDataSetChanged()
  }

  fun addWeather() {
    var weather = Weather()
    weather.name = "Hanoi" + randomInt(1, 20)
    weather.cod = 200
    var main = Main(randomDouble(1.0, 100.0), randomInt(1, 10))
    weather.main = main
    saveData(weather)
  }

  fun saveData(weather: Weather) {
    var id = weatherDao?.insertWeather(weather)
    weather.main?.weather_id = id?.toInt()
    weatherDao?.insertMain(weather.main)
  }

  fun randomInt(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()

  fun randomDouble(from: Double, to: Double) = (Math.random() * (to - from) + from).toDouble()

  fun configRecycleView(context: Context) {
    recycle_main?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    recycle_main?.hasFixedSize()
    recycle_main?.adapter = adapter
  }

  fun tryRxOnKotlin() {
    val _str = listOf<String>("One", "Two", "Three", "Four", "Fire")
    val filter = _str?.filter { it.length >= 4 }.map { it.toUpperCase() }

    println(filter)
  }

  companion object {
    val TAG = "MainActivity"

    fun newIntent(context: Context): Intent {
      val intent = Intent(context, SearchActivity::class.java)
      return intent
    }
  }
}
