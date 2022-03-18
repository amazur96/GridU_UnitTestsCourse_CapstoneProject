package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import androidx.room.*
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

@Dao
interface UsersDao {
    @Query("SELECT * FROM UserDetails")
    fun getUsers(): List<UserDetails>

    @Query("SELECT * FROM UserDetails WHERE id = :id")
    fun getUserById(id: Int): UserDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserDetails)

    @Update
    fun updateUser(user: UserDetails)

    @Delete
    fun deleteUser(user: UserDetails)

    @Query("DELETE FROM UserDetails")
    suspend fun deleteAllUsers()
}
