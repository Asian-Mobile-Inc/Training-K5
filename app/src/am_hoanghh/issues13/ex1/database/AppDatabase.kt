package issues13.ex1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import issues13.ex1.model.UserDao
import issues13.ex1.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
