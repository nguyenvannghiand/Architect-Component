package android.thaihn.kotlindemo.screen.search

import android.content.Context
import android.content.Intent
import android.thaihn.kotlindemo.R
import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.screen.main.adapter.MainAdapter
import android.thaihn.kotlindemo.screen.main.adapter.OnListenerMain
import android.thaihn.kotlindemo.screen.navigation.NavigationActivity
import android.thaihn.kotlindemo.utils.ToastUtil
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.thaihn.kotlinstart.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseActivity(), OnListenerMain, SearchContract.View, View.OnClickListener {

  @Inject
  lateinit var presenter: SearchPresenter<SearchContract.View>
  var adapter: MainAdapter = MainAdapter(ArrayList<Weather>(), this, this)

  override fun getLayoutResource(): Int {
    return R.layout.activity_search
  }

  override fun initVariable() {
    button_search.setOnClickListener(this)
    fab_next.setOnClickListener(this)
  }

  override fun initData() {
    injectDependence()
    presenter.onAttach(this)
    configRecycle()
    setTitle("Dagger, MVP, Retrofit")
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onClickItem(id: Int) {
    // do something
  }

  override fun searchSuccess(item: Weather) {
    var list = ArrayList<Weather>()
    list.add(item)
    adapter.setWeathers(list)
    adapter.notifyDataSetChanged()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        onBackPressed()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun searchFail(message: String?) {
    ToastUtil.quickToast(this, message!!, false)
  }

  override fun showProgress() {
  }

  override fun hideProgress() {
  }

  override fun onClick(view: View?) {
    when (view?.id) {
      R.id.button_search -> {
        presenter.searchWeather(edit_search?.text.toString())
      }
      R.id.fab_next -> {
        var intent = NavigationActivity.newIntent(this)
        startActivity(intent)
      }
    }
  }

  fun configRecycle() {
    recycle_result?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    recycle_result?.setHasFixedSize(true)
    recycle_result?.adapter = adapter
    adapter.notifyDataSetChanged()
  }

  fun injectDependence() {
    DaggerSearchComponent.builder()
        .build()
        .inject(this)
  }

  companion object {
    private val INTENT_STATUS = "status"
    private val TAG = "SearchActivity"

    fun newIntent(context: Context, status: String): Intent {
      val intent = Intent(context, SearchActivity::class.java)
      intent.putExtra(INTENT_STATUS, status)
      return intent
    }
  }
}