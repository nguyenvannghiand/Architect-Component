package android.thaihn.kotlindemo.screen.search

import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.data.repository.WeatherRepository
import android.thaihn.kotlindemo.data.source.WeatherDataSource
import javax.inject.Inject


class SearchPresenter<V : SearchContract.View> : SearchContract.Presenter<V>, WeatherDataSource.OnFetchData<Weather> {

  var view: SearchContract.View? = null
  var repository: WeatherRepository? = null

  @Inject
  constructor() {
    repository = WeatherRepository.getInstance()
  }

  override fun searchWeather(key: String?) {
    repository?.getWeatherRemote(key, this)
  }

  override fun onAttach(view: V) {
    this.view = view
  }

  override fun onDetach() {
    view = null
  }

  override fun onFetchDataSuccess(result: Weather) {
    view?.searchSuccess(result)
  }

  override fun onFetchDataFail(message: String?) {
    view?.searchFail(message)
  }
}
