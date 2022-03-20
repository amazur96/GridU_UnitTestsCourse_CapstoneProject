package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSourceContract
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.RemoteDataSourceContract
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceContract,
    private val localDataSource: LocalDataSourceContract
) : UsersRepositoryContract {

    override suspend fun getUsers(isForceUpdate: Boolean): Result<List<UserDetails>> {
        val response = localDataSource.getUserDetailsList()
        if (isForceUpdate || response !is Result.Success || response.data.isNullOrEmpty()) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (e: Exception) {
                return Result.Error(e)
            }
        }
        return localDataSource.getUserDetailsList()
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

    override suspend fun getUserDetails(userId: Int): Result<UserDetails> {
        return localDataSource.getUserDetails(userId)
    }
}