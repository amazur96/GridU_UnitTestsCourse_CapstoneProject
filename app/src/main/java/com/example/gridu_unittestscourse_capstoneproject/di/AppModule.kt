package com.example.gridu_unittestscourse_capstoneproject.di

import android.content.Context
import androidx.room.Room
import com.example.gridu_unittestscourse_capstoneproject.data.source.UsersRepository
import com.example.gridu_unittestscourse_capstoneproject.data.source.UsersRepositoryContract
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.AppDatabase
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSource
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSourceContract
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.UsersDao
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.NetworkService
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.RemoteDataSource
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.RemoteDataSourceContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://api.github.com"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit) = retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(mainService: NetworkService): RemoteDataSourceContract =
        RemoteDataSource(mainService)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "AppDatabase.db"
        ).build()
    }

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
        remoteDataSource: RemoteDataSourceContract
    ): UsersRepositoryContract =
        UsersRepository(remoteDataSource, localDataSource)
}