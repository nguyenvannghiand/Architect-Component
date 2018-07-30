package android.thaihn.kotlindemo.data.room.dao

import android.thaihn.kotlindemo.data.model.Main
import android.thaihn.kotlindemo.data.model.Weather
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

  @Query("SELECT * FROM weathers WHERE id = :id")
  fun getWeather(id: Int): LiveData<List<Weather>>

  @Query("SELECT * FROM main WHERE weather_id = :id")
  fun getMain(id: Int): LiveData<List<Main>>

  @Query("SELECT * FROM weathers")
  fun getAllWeather(): LiveData<List<Weather>>

  @Query("SELECT * FROM main")
  fun getAllMain(): LiveData<List<Main>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertWeather(weather: Weather?): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMain(main: Main?)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertWeathers(weathers: ArrayList<Weather>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMains(main: List<Main>)

  @Query("DELETE from weathers")
  fun deleteWeathers()
}