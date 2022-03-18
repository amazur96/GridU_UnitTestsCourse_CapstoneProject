package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSourceContract
import javax.inject.Inject

class FakeUserRepository @Inject constructor(
    private val localDataSource: LocalDataSourceContract
) : UsersRepositoryContract {
    override suspend fun getUsers(isForceUpdate: Boolean): Result<List<UserDetails>> {
        return localDataSource.getUserDetailsList()
    }

    suspend fun saveUser(userDetails: UserDetails) {
        return localDataSource.saveUser(userDetails)
    }

    override suspend fun getUserDetails(userId: Int): Result<UserDetails> {
        return localDataSource.getUserDetails(userId)
    }
}