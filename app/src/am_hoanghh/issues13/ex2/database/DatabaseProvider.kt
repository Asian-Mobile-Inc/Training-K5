package issues13.ex2.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private const val APP_DATABASE = "database_training"

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                APP_DATABASE
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
