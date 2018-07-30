package android.thaihn.kotlindemo.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weathers")
class Weather() : Parcelable {

    @ColumnInfo(name = "visibility")
    @SerializedName("visibility")
    var visibility: Int? = null

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = null

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null

    @ColumnInfo(name = "cod")
    @SerializedName("cod")
    var cod: Int? = null

    @Ignore
    @ColumnInfo(name = "main")
    @SerializedName("main")
    var main: Main? = null
}
