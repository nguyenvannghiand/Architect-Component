package android.thaihn.kotlindemo.data.repository

import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.data.source.WeatherDataSource
import android.thaihn.kotlindemo.data.source.local.WeatherLocalDataSource
import android.thaihn.kotlindemo.data.source.remote.WeatherRemoteDataSource

class WeatherRepository : WeatherDataSource.RemoteDataSource {

  var remote: WeatherDataSource.RemoteDataSource
  var local: WeatherDataSource.LocalDataSource


  override fun getWeatherRemote(key: String?, listener: WeatherDataSource.OnFetchData<Weather>) {
    remote.getWeatherRemote(key, listener)
  }

  constructor(remote: WeatherDataSource.RemoteDataSource,
              local: WeatherDataSource.LocalDataSource) {
    this.remote = remote
    this.local = local
  }

  private object Holder {
    val instance = WeatherRepository(WeatherRemoteDataSource.getInstance(),
        WeatherLocalDataSource.getInstance())
  }

  companion object {

    @JvmStatic
    fun getInstance(): WeatherRepository {
      return Holder.instance
    }
  }
}