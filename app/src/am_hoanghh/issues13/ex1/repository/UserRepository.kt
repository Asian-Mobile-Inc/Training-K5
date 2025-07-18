package issues13.ex1.repository

import issues13.ex1.model.UserDao
import issues13.ex1.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    suspend fun getUsers(): List<UserModel> =
        withContext(Dispatchers.IO) {
            val data = userDao.readUsers()
            var cnt = 0
            data.forEach { it.countNumber = cnt++ }
            data
        }

    suspend fun insertUser(userModel: UserModel) =
        withContext(Dispatchers.IO) {
            userDao.addNewUser(userModel)
        }

    suspend fun deleteUser(userModel: UserModel) =
        withContext(Dispatchers.IO) {
            userDao.deleteUser(userModel)
        }

    suspend fun deleteAll() =
        withContext(Dispatchers.IO) {
            userDao.deleteAll()
        }
}
