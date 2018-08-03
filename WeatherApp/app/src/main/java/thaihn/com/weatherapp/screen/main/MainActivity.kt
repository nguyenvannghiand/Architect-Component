package thaihn.com.weatherapp.screen.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import thaihn.com.weatherapp.BR
import thaihn.com.weatherapp.BuildConfig.APPLICATION_ID
import thaihn.com.weatherapp.R
import thaihn.com.weatherapp.databinding.ActivityMainBinding
import thaihn.com.weatherapp.screen.base.activity.BaseBindingActivity
import thaihn.com.weatherapp.screen.main.adapter.HourlyAdapter
import thaihn.com.weatherapp.screen.main.adapter.InfoAdapter
import thaihn.com.weatherapp.screen.main.adapter.WeeklyAdapter
import thaihn.com.weatherapp.util.Constants
import thaihn.com.weatherapp.util.SharedPrefs

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {

    private val TAG = "MainActivity"

    private val PERMISSIONS_REQUEST_CODE = 29
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModel: MainViewModel
        get() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    override val layoutResource: Int
        get() = R.layout.activity_main

    override fun initVariable(savedInstanceState: Bundle?) {
        viewDataBinding?.recycleHourly?.apply {
            adapter = HourlyAdapter(listOf())
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL, false)
        }
        viewDataBinding?.recycleWeekly?.apply {
            adapter = WeeklyAdapter(listOf())
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.VERTICAL, false)
        }
        viewDataBinding?.recycleInfo?.apply {
            adapter = InfoAdapter(listOf())
            hasFixedSize()
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData(savedInstanceState: Bundle?) {
        changeColorStatusBar()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onActivityDestroyed()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLastLocation()
                else -> {
                    showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                            View.OnClickListener {
                                // Build intent that displays the App settings screen.
                                val intent = Intent().apply {
                                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                    data = Uri.fromParts("package", APPLICATION_ID, null)
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                startActivity(intent)
                            })
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun changeColorStatusBar() {
        var window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_background)
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        SharedPrefs.instance.put(Constants.PREFERENCE_LOCATION_LAT,
                                task.result.latitude)
                        SharedPrefs.instance.put(Constants.PREFERENCE_LOCATION_LONG,
                                task.result.longitude)
                        viewModel.loadData()
                    } else {
                        Log.w(TAG, "getLastLocation:exception", task.exception)
                        showSnackbar(R.string.no_location_detected)
                    }
                }
    }

    private fun checkPermissions() =
            ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar(R.string.permission_rationale, android.R.string.ok, View.OnClickListener {
                // Request permission
                startLocationPermissionRequest()
            })

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    /**
     * Shows a [Snackbar].
     *
     * @param snackStrId The id for the string resource for the Snackbar text.
     * @param actionStrId The text of the action item.
     * @param listener The listener associated with the Snackbar action.
     */
    private fun showSnackbar(
            snackStrId: Int,
            actionStrId: Int = 0,
            listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), getString(snackStrId),
                LENGTH_INDEFINITE)
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }
}
