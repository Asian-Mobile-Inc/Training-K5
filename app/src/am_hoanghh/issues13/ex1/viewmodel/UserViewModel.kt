package issues13.ex1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import issues13.ex1.model.UserModel
import issues13.ex1.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _users = MutableLiveData<MutableList<UserModel>>()
    val users: LiveData<MutableList<UserModel>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() = viewModelScope.launch {
        val data = repository.getUsers()
        _users.postValue(data)
    }

    fun addUser(userModel: UserModel) = viewModelScope.launch {
        repository.insertUser(userModel)
        fetchUsers()
    }

    fun deleteUser(userModel: UserModel) = viewModelScope.launch {
        repository.deleteUser(userModel)
        fetchUsers()
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
        fetchUsers()
    }
}
