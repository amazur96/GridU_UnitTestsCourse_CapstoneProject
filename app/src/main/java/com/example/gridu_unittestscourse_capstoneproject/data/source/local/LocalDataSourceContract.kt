package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

interface LocalDataSourceContract {
    suspend fun getUserDetailsList(): Result<List<UserDetails>>
    suspend fun getUserDetails(id: Int): Result<UserDetails>
    suspend fun saveUser(userDetails: UserDetails)
    suspend fun deleteAllUsers()
}