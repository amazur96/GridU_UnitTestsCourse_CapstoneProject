package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.User
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val remoteDataSource: MainDataSource
) {
    suspend fun getUsers(): Result<List<UserDetails>> {
        val response = remoteDataSource.getUsers()
        return if (response.isSuccessful) {
            return if (response.body().isNullOrEmpty()) {
                Result.Success(null)
            } else {
                getUsersDetails(response.body()!!)
            }
        } else {
            Result.Error(Exception(response.message()))
        }
    }

    private suspend fun getUsersDetails(userList: List<User>): Result<List<UserDetails>> {
        val usersDetails = mutableListOf<UserDetails>()
        for (it in userList) {
            val response = remoteDataSource.getUserDetails(it.login)
            if (response.isSuccessful && response.body() != null) {
                usersDetails.add(response.body()!!)
            }
        }
        return Result.Success(usersDetails)
    }
}