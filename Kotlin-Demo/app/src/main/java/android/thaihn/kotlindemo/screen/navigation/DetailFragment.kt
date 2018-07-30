package android.thaihn.kotlindemo.screen.navigation


import android.content.Context
import android.os.Bundle
import android.thaihn.kotlindemo.R
import android.thaihn.kotlindemo.data.model.Weather
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.thaihn.kotlinstart.screen.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : BaseFragment() {

  private var mListener: OnFragmentInteractionListener? = null

  var image: ImageView? = null
  var text_name: TextView? = null
  var text_temp: TextView? = null
  var text_visibility: TextView? = null
  var text_id: TextView? = null

  override fun getLayoutResource(): Int {
    return R.layout.fragment_detail
  }

  override fun initVariable(saveInstanceState: Bundle?, rootView: View) {
    image = rootView.findViewById(R.id.image)
    text_name = rootView.findViewById(R.id.text_name)
    text_temp = rootView.findViewById(R.id.text_temp)
    text_visibility = rootView.findViewById(R.id.text_visibility)
    text_id = rootView.findViewById(R.id.text_id)
  }

  override fun initData(saveInstanceState: Bundle?) {
    val weather: Weather? = arguments?.getParcelable(BUNDLE_WEATHER)
    mListener?.fragmentCreate("Detail Fragment")
    setupUi(weather!!)
  }

  fun setupUi(weather: Weather) {
    image?.setImageResource(R.drawable.ic_sun)
    text_name?.text = weather?.name
    text_temp?.text = weather?.main?.temp.toString()
    text_visibility?.text = weather?.visibility.toString()
    text_id?.text = weather?.id.toString()
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

  companion object {
    var TAG = "DetailFragment"
    var BUNDLE_WEATHER = "Weather"

    @JvmStatic
    fun newInstance() = DetailFragment()
  }

  interface OnFragmentInteractionListener {
    fun fragmentCreate(title: String)
  }
}
