package com.example.gridu_unittestscourse_capstoneproject.di

import android.content.Context
import androidx.room.Room
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.AppDatabase
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.LocalDataSource
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.UsersDao
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.NetworkService
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
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
    fun provideRetrofit(BASE_URL: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().apply {
                        addHeader(
                            "Authorization",
                            Credentials.basic("amazur96", "121232421Ycbu3")
                        )
                    }.build()
                )
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
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
    fun provideRemoteDataSource(mainService: NetworkService): RemoteDataSource =
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
    fun provideLocalDataSource(usersDao: UsersDao): LocalDataSource =
        LocalDataSource(usersDao)
}