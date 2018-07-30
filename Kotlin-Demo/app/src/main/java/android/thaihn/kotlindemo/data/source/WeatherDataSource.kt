package android.thaihn.kotlindemo.data.source

import android.thaihn.kotlindemo.data.model.Weather

interface WeatherDataSource {

  interface RemoteDataSource {
    fun getWeatherRemote(key: String?, listener: OnFetchData<Weather>)
  }

  interface OnFetchData<T> {

    fun onFetchDataSuccess(result: T)

    fun onFetchDataFail(message: String?)
  }

  interface LocalDataSource {
  }
}
