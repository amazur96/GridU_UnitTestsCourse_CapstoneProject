package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

class LocalDataSource(
    private val usersDao: UsersDao
) {

    suspend fun getUserDetails(): Result<List<UserDetails>> {
        return try {
            Result.Success(usersDao.getUsers())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun saveUser(userDetails: UserDetails) {
        usersDao.insertUser(userDetails)
    }

    suspend fun deleteAllUsers() {
        usersDao.deleteAllUsers()
    }
}