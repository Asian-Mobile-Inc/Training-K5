package issues13.ex1.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import issues13.ex1.database.DatabaseProvider
import issues13.ex1.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {
    private val db = DatabaseProvider.getDatabase(context)
    private val userDao = db.userDao()
    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = userDao.readUsers()
            var cnt = 0
            data.forEach { it.countNumber = cnt++ }
            _users.postValue(data)
        }
    }

    fun addUser(userModel: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.addNewUser(userModel)
            fetchUsers()
        }
    }

    fun deleteUser(userModel: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteUser(userModel)
            fetchUsers()
        }
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAll()
            fetchUsers()
        }
    }
}
