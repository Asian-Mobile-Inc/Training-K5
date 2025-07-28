package com.example.asian.issue13.ex1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asian.issue13.ex1.database.UserDatabase
import com.example.asian.issue13.ex1.model.User
import com.example.asian.issue13.ex1.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val allUser: LiveData<List<User>>
    private val repository: UserRepository
    init {
        val userDAO = UserDatabase.getDatabase(application).userDAO()
        repository = UserRepository(userDAO)
        allUser = repository.allUser
    }
    fun addUser(user: User) = viewModelScope.launch {
        repository.insertUser(user)
    }
    fun deleteUser(user: User) = viewModelScope.launch {
        repository.deleteUser(user)
    }
    fun deleteAllUser() = viewModelScope.launch {
        repository.deleteAllUser()
    }
    fun updatePosition(users: List<User>, userDeleteId: Int): List<User> {
        var index = 0
        val newUsers = mutableListOf<User>()
        for (user in users) {
            if (user.id > userDeleteId) {
                val us = User(user.name, user.age)
                us.id = user.id
                us.position = index
                newUsers.add(us)
            } else {
                newUsers.add(user)
            }
            index++
        }
        return newUsers
    }
}
