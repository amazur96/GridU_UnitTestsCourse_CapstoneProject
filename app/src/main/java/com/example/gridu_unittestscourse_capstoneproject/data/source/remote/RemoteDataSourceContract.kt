package com.example.gridu_unittestscourse_capstoneproject.data.source.remote

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

interface RemoteDataSourceContract {
    suspend fun getUserDetails(): Result<List<UserDetails>>
}
