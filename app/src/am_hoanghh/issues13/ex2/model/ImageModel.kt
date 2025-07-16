package issues13.ex2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import issues13.ex2.model.ImageModel.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ImageModel(
    @SerializedName("image_id")
    @ColumnInfo(name = "image_id")
    @PrimaryKey
    val imageId: String,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    var isFavorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "image"
    }
}
