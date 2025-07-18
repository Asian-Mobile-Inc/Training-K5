package issues13.ex1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import issues13.ex1.model.UserModel
import issues13.ex1.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getUsers()
            _users.postValue(data)
        }
    }

    fun addUser(userModel: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertUser(userModel)
            fetchUsers()
        }
    }

    fun deleteUser(userModel: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteUser(userModel)
            fetchUsers()
        }
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAll()
            fetchUsers()
        }
    }
}
