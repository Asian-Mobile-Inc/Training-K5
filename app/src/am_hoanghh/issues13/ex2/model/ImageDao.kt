package issues13.ex2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import issues13.ex2.model.ImageModel.Companion.TABLE_NAME

@Dao
interface ImageDao {
    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun selectAllImages(): MutableList<ImageModel>

    @Query("SELECT * FROM $TABLE_NAME WHERE is_favorite == :favorite")
    suspend fun selectFavoriteImages(favorite: Boolean): MutableList<ImageModel>

    @Transaction
    suspend fun insertAllSafely(items: MutableList<ImageModel>) {
        items.forEach { insert(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ImageModel)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("UPDATE $TABLE_NAME SET is_favorite = :isFavorite WHERE image_id = :imageId")
    suspend fun updateFavoriteImage(imageId: String, isFavorite: Boolean)
}
