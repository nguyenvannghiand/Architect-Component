package thaihn.com.weatherapp.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

object ToastUtils {

    fun quickToast(context: Context, msg: String, duration: Int) {
        val toast = Toast.makeText(context, msg, duration)
        val textView = toast.view.findViewById<View>(android.R.id.message) as TextView
        textView.apply {
            Gravity.CENTER
            setPadding(10, 0, 10, 0)
        }
        toast.show()
    }
}
