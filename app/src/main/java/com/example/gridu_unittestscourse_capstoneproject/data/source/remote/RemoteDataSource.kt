package com.example.gridu_unittestscourse_capstoneproject.data.source.remote

import com.example.gridu_unittestscourse_capstoneproject.data.model.User
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.MainDataSource
import retrofit2.Response

class RemoteDataSource (
    private val networkService: NetworkService
) : MainDataSource {
    override suspend fun getUsers(): Response<List<User>> {
        return networkService.getUsers()
    }

    override suspend fun getUserDetails(login: String): Response<UserDetails> {
        return networkService.getUserDetails(login)
    }
}