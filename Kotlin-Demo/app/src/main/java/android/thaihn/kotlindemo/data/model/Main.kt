package android.thaihn.kotlindemo.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "main")
class Main() : Parcelable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "weather_id")
    @ForeignKey(entity = Weather::class, parentColumns = arrayOf("id"), childColumns = arrayOf(
            "weather_id"))
    var weather_id: Int? = null

    @ColumnInfo(name = "temp")
    @SerializedName("temp")
    var temp: Double? = null

    @ColumnInfo(name = "temp_min")
    @SerializedName("temp_min")
    var temp_min: Float? = null

    @ColumnInfo(name = "temp_max")
    @SerializedName("temp_max")
    var temp_max: Float? = null

    @ColumnInfo(name = "humidity")
    @SerializedName("humidity")
    var humidity: Int? = null

    @ColumnInfo(name = "pressure")
    @SerializedName("pressure")
    var pressure: Int? = null

    constructor(temp: Double, humidity: Int) : this() {
        this.temp = temp
        this.humidity = humidity

    }
}