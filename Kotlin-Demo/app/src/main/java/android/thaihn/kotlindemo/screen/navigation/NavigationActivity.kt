package android.thaihn.kotlindemo.screen.navigation

import android.content.Context
import android.content.Intent
import android.thaihn.kotlindemo.R
import android.view.MenuItem
import androidx.navigation.Navigation
import com.thaihn.kotlinstart.screen.base.BaseActivity

class NavigationActivity : BaseActivity(), HomeFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

  override fun getLayoutResource(): Int {
    return R.layout.fragment_fragment
  }

  override fun initVariable() {
  }

  override fun initData() {
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    return Navigation.findNavController(this, R.id.my_nav_home_fragment).navigateUp()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun fragmentCreate(title: String) {
    supportActionBar?.setTitle(title)
    listenerBackstack()
  }

  fun listenerBackstack() {
    // TODO fixme
    val fragmenStackChange = {
      if (supportFragmentManager?.backStackEntryCount!! <= 1) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
      } else {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
    }
    supportFragmentManager?.addOnBackStackChangedListener { fragmenStackChange }
  }

  companion object {
    private val TAG = "NavigationActivity"

    fun newIntent(context: Context): Intent {
      val intent = Intent(context, NavigationActivity::class.java)
      return intent
    }
  }
}
