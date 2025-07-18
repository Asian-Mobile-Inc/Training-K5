package issues13.ex1.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun readUsers(): List<UserModel>

    @Insert
    suspend fun addNewUser(userModel: UserModel)

    @Delete
    suspend fun deleteUser(userModel: UserModel)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}
