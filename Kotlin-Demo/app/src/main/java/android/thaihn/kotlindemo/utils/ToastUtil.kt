package android.thaihn.kotlindemo.utils

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

class ToastUtil private constructor() {

  companion object {
    fun quickToast(context: Context, message: String) {
      quickToast(context, message, false)
    }

    fun quickToast(context: Context, message: String, is_long: Boolean) {
      var toast = Toast.makeText(context, message, if (is_long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
      var text = toast.view.findViewById<TextView>(android.R.id.message)
      text?.gravity = Gravity.CENTER
      text?.setPadding(10, 0, 10, 0)
      toast.show()
    }
  }
}