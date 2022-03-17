package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

class LocalDataSource(
    private val usersDao: UsersDao
) : LocalDataSourceContract{

    override suspend fun getUserDetailsList(): Result<List<UserDetails>> {
        return try {
            Result.Success(usersDao.getUsers())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUserDetails(id: Int): Result<UserDetails> {
        return try {
            Result.Success(usersDao.getUserById(id))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveUser(userDetails: UserDetails) {
        usersDao.insertUser(userDetails)
    }

    override suspend fun deleteAllUsers() {
        usersDao.deleteAllUsers()
    }
}