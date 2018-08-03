package thaihn.com.weatherapp.screen.main

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import thaihn.com.weatherapp.data.model.DataDaily
import thaihn.com.weatherapp.data.model.DataHourly
import thaihn.com.weatherapp.data.model.Info
import thaihn.com.weatherapp.data.model.Response
import thaihn.com.weatherapp.data.repository.WeatherRepository
import thaihn.com.weatherapp.data.source.remote.BaseNetwork
import thaihn.com.weatherapp.screen.base.viewmodel.BaseViewModel
import thaihn.com.weatherapp.util.Constants
import thaihn.com.weatherapp.util.SharedPrefs
import thaihn.com.weatherapp.util.Utils
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : BaseViewModel() {

    private val TAG = "MainViewModel"

    private var repository: WeatherRepository? = null

    val textTemp = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val status = MutableLiveData<String>()

    val data: ObservableField<Response> = ObservableField()
    val hourlyItems: ObservableList<DataHourly> = ObservableArrayList()
    val weeklyItems: ObservableList<DataDaily> = ObservableArrayList()
    val infoItems: ObservableList<Info> = ObservableArrayList()

    init {
        title.value = "Hà Nội"
        textTemp.value = "- -"
        loading.value = false
        repository = WeatherRepository(BaseNetwork().providerWeatherApi())
    }

    @SuppressLint("CheckResult")
    fun loadData() {
        val lat = SharedPrefs.instance[Constants.PREFERENCE_LOCATION_LAT, Double::class.java, 1.0]
        val long = SharedPrefs.instance[Constants.PREFERENCE_LOCATION_LONG, Double::class.java, 1.0]
        loading.postValue(true)
        repository!!.getWeather(lat, long)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { it ->
                            handleResponse(it)
                        },
                        { it ->
                            handleError(it)
                        })
    }

    private fun handleResponse(response: Response) {
        loading.value = false
        textTemp.value = Utils.changeTempFToC(
                response.currently.temperature).toString() + Utils.makeTemp()
        hourlyItems.clear()
        hourlyItems.addAll(response.hourly.data)

        weeklyItems.clear()
        weeklyItems.addAll(response.daily.data)

        status.value = makeStatus(response)

        infoItems.clear()
        infoItems.addAll(makeInfo(response))
    }

    private fun handleError(message: Throwable) {
        loading.value = false
        message.printStackTrace()
    }

    fun onActivityDestroyed() {
        data.set(null)
        hourlyItems.clear()
        weeklyItems.clear()
    }

    fun onRefresh() {
        loadData()
    }

    fun makeInfo(response: Response): List<Info> {
        var infoList = ArrayList<Info>()
        infoList.add(
                Info("SUNRISE", Utils.convertTime("HH:mm", response.daily.data[0].sunriseTime)))
        infoList.add(
                Info("SUNSET", Utils.convertTime("HH:mm", response.daily.data[0].sunsetTime)))
        infoList.add(
                Info("CHANGE OF RAIN", "80%"))
        var humidity = (response.currently.humidity * 100).toInt().toString() + "%"
        infoList.add(
                Info("HUMIDITY", humidity))
        infoList.add(Info("WIND", response.currently.windSpeed.toInt().toString() + " kph"))
        infoList.add(Info("PRESSURE", response.currently.pressure.toInt().toString() + " hPa"))
        infoList.add(Info("VISIBILITY", makeVisibility(response.currently.visibility)))
        infoList.add(Info("UV INDEX", response.currently.uvIndex.toString()))
        return infoList
    }

    private fun makeVisibility(visibility: Double): String {
        var _visi = "%.1f".format(visibility)
        return _visi + " km"
    }

    private fun makeStatus(response: Response): String {
        val maxTemp = Utils.changeTempFToC(
                response.daily.data[0].temperatureHigh).toString() + Utils.makeTemp()
        val minTemp = Utils.changeTempFToC(
                response.daily.data[0].temperatureLow).toString() + Utils.makeTemp()
        return "Today: ${response.currently.summary}. The high will be $maxTemp. ${response.daily.data[0].summary} With a low of $minTemp."
    }
}
