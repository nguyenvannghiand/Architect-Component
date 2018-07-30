package android.thaihn.kotlindemo.service

import android.thaihn.kotlindemo.data.model.Weather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
  @GET("weather")
  fun searchWeather(@Query("q") key: String,
                    @Query("appid") id: String): Observable<Weather>
}