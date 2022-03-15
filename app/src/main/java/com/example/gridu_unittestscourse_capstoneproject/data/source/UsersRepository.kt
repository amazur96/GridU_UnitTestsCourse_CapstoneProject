package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSource
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.RemoteDataSource
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getUsers(isForceUpdate: Boolean): Result<List<UserDetails>> {
        val usersDetailsResponse = localDataSource.getUserDetails()
        if (isForceUpdate
            || usersDetailsResponse !is Result.Success
            || usersDetailsResponse.data.isNullOrEmpty()) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (e: Exception) {
                return Result.Error(e)
            }
        }
        return localDataSource.getUserDetails()
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        val response = remoteDataSource.getUserDetails()
        if (response is Result.Success && response.data != null) {
            localDataSource.deleteAllUsers()
            response.data.forEach {
                localDataSource.saveUser(it)
            }
        } else if (response is Result.Error) {
            throw response.exception
        }
    }
}