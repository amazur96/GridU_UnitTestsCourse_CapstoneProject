package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.data.model.User
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import retrofit2.Response

interface MainDataSource {
    suspend fun getUsers(): Response<List<User>>
    suspend fun getUserDetails(login: String): Response<UserDetails>
}