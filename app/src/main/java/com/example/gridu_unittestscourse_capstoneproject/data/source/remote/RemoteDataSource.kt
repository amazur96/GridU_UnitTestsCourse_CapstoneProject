package com.example.gridu_unittestscourse_capstoneproject.data.source.remote

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.User
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

class RemoteDataSource (
    private val networkService: NetworkService
) : RemoteDataSourceContract{

    override suspend fun getUserDetails(): Result<List<UserDetails>> {
        val userResponse = networkService.getUsers()
        return if (userResponse.isSuccessful) {
            return if (userResponse.body().isNullOrEmpty()) {
                Result.Success(null)
            } else {
                loadUserDetails(userResponse.body()!!)
            }
        } else {
            Result.Error(Exception(userResponse.message()))
        }
    }

    private suspend fun loadUserDetails(users: List<User>): Result<List<UserDetails>> {
        val usersDetails = mutableListOf<UserDetails>()
        val response = networkService.getUserDetails(users[0].login)
        if (response.isSuccessful && response.body() != null) {
            usersDetails.add(response.body()!!)
        }
//        for (it in userList) {
//            val response = remoteDataSource.getUserDetails(it.login)
//            if (response.isSuccessful && response.body() != null) {
//                usersDetails.add(response.body()!!)
//            }
//        }
        return Result.Success(usersDetails)
    }
}
