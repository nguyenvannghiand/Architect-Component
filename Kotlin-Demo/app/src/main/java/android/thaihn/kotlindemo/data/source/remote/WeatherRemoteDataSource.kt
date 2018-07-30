package android.thaihn.kotlindemo.data.source.remote

import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.data.source.WeatherDataSource
import android.thaihn.kotlindemo.service.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRemoteDataSource private constructor() : WeatherDataSource.RemoteDataSource {

  var listener: WeatherDataSource.OnFetchData<Weather>? = null

  override fun getWeatherRemote(key: String?, listener: WeatherDataSource.OnFetchData<Weather>) {
    this.listener = listener
    searchData(key)
  }

  private object Holder {
    val instance = WeatherRemoteDataSource()
  }

  companion object {

    @JvmStatic
    fun getInstance(): WeatherRemoteDataSource {
      return Holder.instance
    }
  }

  fun searchData(key: String?) {
    val requestInterface = Retrofit.Builder()
        .baseUrl("https://samples.openweathermap.org/data/2.5/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(WeatherAPI::class.java)
    requestInterface.searchWeather(key + ",uk", "b6907d289e10d714a6e88b30761fae22")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(
            { n -> handleResponse(n) },
            { e -> handleError(e.message) })
  }

  fun handleResponse(weather: Weather) {
    listener?.onFetchDataSuccess(weather)
  }

  fun handleError(error: String?) {
    listener?.onFetchDataFail(error)
  }
}