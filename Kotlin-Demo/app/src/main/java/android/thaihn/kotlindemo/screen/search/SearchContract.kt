package android.thaihn.kotlindemo.screen.search

import android.thaihn.kotlindemo.data.model.Weather
import com.thaihn.kotlinstart.screen.base.BasePresenter
import com.thaihn.kotlinstart.screen.base.BaseView

interface SearchContract {

  interface View : BaseView {
    fun searchSuccess(item: Weather)

    fun searchFail(message: String?)
  }

  interface Presenter<V : BaseView> : BasePresenter<V> {
    fun searchWeather(key: String?)
  }
}
