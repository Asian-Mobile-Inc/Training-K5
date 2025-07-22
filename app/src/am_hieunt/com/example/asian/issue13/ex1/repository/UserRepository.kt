package com.example.asian.issue13.ex1.repository

import androidx.lifecycle.LiveData
import com.example.asian.issue13.ex1.model.User
import com.example.asian.issue13.ex1.database.UserDAO

class UserRepository(private val userDAO: UserDAO) {
    val allUser: LiveData<List<User>> = userDAO.getAllUser()
    suspend fun insertUser(user: User) {
        userDAO.insertUser(user)
    }
    suspend fun deleteUser(user: User) {
        userDAO.deleteUser(user)
    }
    suspend fun deleteAllUser() {
        userDAO.deleteAllUser()
    }
}