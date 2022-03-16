package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

interface UsersRepositoryContract {
    suspend fun getUsers(isForceUpdate: Boolean): Result<List<UserDetails>>
}