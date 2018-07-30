package android.thaihn.kotlindemo.data.room.db

import android.content.Context
import android.thaihn.kotlindemo.data.model.Main
import android.thaihn.kotlindemo.data.model.Weather
import android.thaihn.kotlindemo.data.room.dao.WeatherDao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Weather::class, Main::class), version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun getWeatherDao(): WeatherDao

  companion object {
    private val database_name = "weatherdb"
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
      if (instance == null) {
        instance = Room.databaseBuilder(context, AppDatabase::class.java, database_name)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
      }
      return instance
    }

    fun removeInstance() {
      instance = null
    }
  }

}
