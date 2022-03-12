package com.example.gridu_unittestscourse_capstoneproject.data.source.remote

import com.example.gridu_unittestscourse_capstoneproject.data.model.User
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("/users/{login}")
    suspend fun getUserDetails(@Path("login") login: String): Response<UserDetails>
}