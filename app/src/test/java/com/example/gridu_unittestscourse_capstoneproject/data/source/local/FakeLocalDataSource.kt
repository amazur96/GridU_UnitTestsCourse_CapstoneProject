package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

class FakeLocalDataSource(
    private var users: MutableList<UserDetails>?
) : LocalDataSourceContract{
    override suspend fun getUserDetailsList(): Result<List<UserDetails>> {
        users?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Local data source fail..."))
    }

    override suspend fun getUserDetails(id: Int): Result<UserDetails> {
        users?.find { it.id == id }?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Local data source fail..."))
    }

    override suspend fun saveUser(userDetails: UserDetails) {
        users?.add(userDetails)
    }

    override suspend fun deleteAllUsers() {
        users?.clear()
    }
}