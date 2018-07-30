package android.thaihn.kotlindemo.screen.navigation


import android.content.Context
import android.os.Bundle
import android.thaihn.kotlindemo.R
import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.data.repository.WeatherRepository
import android.thaihn.kotlindemo.data.source.WeatherDataSource
import android.thaihn.kotlindemo.screen.main.adapter.MainAdapter
import android.thaihn.kotlindemo.screen.main.adapter.OnListenerMain
import android.thaihn.kotlindemo.utils.ToastUtil
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thaihn.kotlinstart.screen.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment(), OnListenerMain, View.OnClickListener, WeatherDataSource.OnFetchData<Weather> {

  private var mRecycleResult: RecyclerView? = null

  private var mRepository: WeatherRepository? = null
  private var mAdapter: MainAdapter? = null
  private var mListener: OnFragmentInteractionListener? = null

  override fun getLayoutResource(): Int {
    return R.layout.fragment_home
  }

  override fun initVariable(saveInstanceState: Bundle?, rootView: View) {
    mRecycleResult = rootView.findViewById(R.id.recycle_result)
    rootView.findViewById<Button>(R.id.button_search).setOnClickListener(this)
    mListener?.fragmentCreate("Home Fragment")
  }

  override fun initData(saveInstanceState: Bundle?) {
    configRecycle()
    mRepository = WeatherRepository.getInstance()
  }

  override fun onClickItem(id: Int) {
    var bundle = Bundle()
    bundle.putString("name", "Thaihn")
    var weather = mAdapter?.getWeathers()?.get(id)
    bundle.putParcelable(DetailFragment.BUNDLE_WEATHER, weather)
    NavHostFragment.findNavController(this).navigate(R.id.detailFragment, bundle)
  }

  override fun onClick(view: View?) {
    when (view?.id) {
      R.id.button_search -> {
        mRepository?.getWeatherRemote("", this)
      }
    }
  }

  override fun onFetchDataFail(message: String?) {
    ToastUtil.quickToast(activity!!, message!!)
  }

  override fun onFetchDataSuccess(result: Weather) {
    var list = ArrayList<Weather>()
    list.add(result)
    mAdapter?.setWeathers(list)
    mAdapter?.notifyDataSetChanged()
  }

  override fun onAttach(context: Context?) {
    if (context is OnFragmentInteractionListener) {
      mListener = context
    } else {
      throw RuntimeException(
          context.toString() + " must be implement OnFragmentInteractionListener")
    }
    super.onAttach(context)
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  fun configRecycle() {
    mAdapter = MainAdapter(ArrayList<Weather>(), context!!, this)
    mRecycleResult?.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL,
        false)
    mRecycleResult?.setHasFixedSize(true)
    mRecycleResult?.adapter = mAdapter
    mAdapter?.notifyDataSetChanged()
  }

  companion object {
    var TAG = "HomeFragment"

    @JvmStatic
    fun newInstance() = HomeFragment()
  }

  interface OnFragmentInteractionListener {
    fun fragmentCreate(title: String)
  }
}
