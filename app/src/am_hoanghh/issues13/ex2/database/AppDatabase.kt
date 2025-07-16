package issues13.ex2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import issues13.ex2.model.ImageDao
import issues13.ex2.model.ImageModel

@Database(entities = [ImageModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
