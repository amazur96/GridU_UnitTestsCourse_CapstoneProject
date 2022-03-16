package com.example.gridu_unittestscourse_capstoneproject.data.source.remote

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

class FakeRemoteDataSource(
    private val users: List<UserDetails>?
) : RemoteDataSourceContract{
    override suspend fun getUserDetails(): Result<List<UserDetails>> {
        users?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Remote data source fail..."))
    }
}