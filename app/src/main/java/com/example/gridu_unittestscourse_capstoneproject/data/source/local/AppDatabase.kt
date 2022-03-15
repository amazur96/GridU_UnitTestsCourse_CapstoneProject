package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails

@Database(entities = [UserDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}