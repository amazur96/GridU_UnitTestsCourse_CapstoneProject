package com.example.gridu_unittestscourse_capstoneproject.di

import android.content.Context
import androidx.room.Room
import com.example.gridu_unittestscourse_capstoneproject.data.source.FakeUserRepository
import com.example.gridu_unittestscourse_capstoneproject.data.source.UsersRepositoryContract
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.AppDatabase
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSource
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSourceContract
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

    @Provides
    @Singleton
    fun provideUsersDao(appDatabase: AppDatabase) = appDatabase.usersDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(usersDao: UsersDao): LocalDataSourceContract =
        LocalDataSource(usersDao)

    @Provides
    fun provideUsersRepository(
        localDataSource: LocalDataSourceContract,
    ): UsersRepositoryContract = FakeUserRepository(localDataSource)
}