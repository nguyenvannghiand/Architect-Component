package thaihn.com.weatherapp.data.repository

import io.reactivex.Observable
import thaihn.com.weatherapp.BuildConfig
import thaihn.com.weatherapp.data.model.Response
import thaihn.com.weatherapp.data.source.remote.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        val weatherApi: WeatherApi
) {

    fun getWeather(latitude: Double,
            longitude: Double): Observable<Response> = weatherApi.getWeather(
            lat = latitude, long = longitude, key = BuildConfig.API_KEY)
}
