package issues13.ex1.repository

import issues13.ex1.model.UserDao
import issues13.ex1.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    suspend fun getUsers(): MutableList<UserModel> {
        val data = userDao.readUsers()
        var cnt = 0
        data.forEach { it.countNumber = cnt++ }
        return data
    }

    suspend fun insertUser(userModel: UserModel) =
        userDao.addNewUser(userModel)

    suspend fun deleteUser(userModel: UserModel) =
        userDao.deleteUser(userModel)

    suspend fun deleteAll() =
        userDao.deleteAll()
}
