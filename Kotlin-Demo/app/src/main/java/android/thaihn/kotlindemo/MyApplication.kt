package android.thaihn.kotlindemo

import android.app.Application
import android.thaihn.kotlindemo.data.room.db.AppDatabase

class MyApplication : Application() {

  companion object {
    var database: AppDatabase? = null
    var application: MyApplication? = null
  }

  override fun onCreate() {
    super.onCreate()
    database = AppDatabase.getInstance(this)
    application = this
  }
}