package android.thaihn.kotlindemo.data.source.local

import android.thaihn.kotlindemo.data.source.WeatherDataSource

class WeatherLocalDataSource : WeatherDataSource.LocalDataSource {

  private object Holder {
    val instance = WeatherLocalDataSource()
  }

  companion object {

    @JvmStatic
    fun getInstance(): WeatherLocalDataSource {
      return Holder.instance
    }
  }
}