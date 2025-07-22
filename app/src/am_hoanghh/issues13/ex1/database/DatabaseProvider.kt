package issues13.ex1.database

import android.content.Context
import androidx.room.Room
import kotlin.concurrent.Volatile

object DatabaseProvider {
    private const val APP_DATABASE = "app_database"

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            APP_DATABASE
        ).build()
        INSTANCE = instance
        instance
    }
}
