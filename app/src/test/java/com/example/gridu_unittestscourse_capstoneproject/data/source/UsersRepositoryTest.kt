package com.example.gridu_unittestscourse_capstoneproject.data.source

import com.example.gridu_unittestscourse_capstoneproject.MainCoroutineRule
import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.local.FakeLocalDataSource
import com.example.gridu_unittestscourse_capstoneproject.data.source.remote.FakeRemoteDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var repository: UsersRepositoryContract

    private val localUserList = mutableListOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
    )
    private val remoteUserList = mutableListOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
        UserDetails(3), UserDetails(4)
    )
    private val emptyUserList = mutableListOf<UserDetails>()

    @Test
    fun getUsers_requestsAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest{
        localDataSource = FakeLocalDataSource(localUserList)
        remoteDataSource = FakeRemoteDataSource(remoteUserList)
        repository = UsersRepository(remoteDataSource, localDataSource)

        val users = repository.getUsers(true) as Result.Success

        assertThat(users.data).isEqualTo(remoteUserList)
    }

    @Test
    fun getUsers_requestsAllTasksFromLocalDataSource() = mainCoroutineRule.runBlockingTest{
        localDataSource = FakeLocalDataSource(localUserList)
        remoteDataSource = FakeRemoteDataSource(remoteUserList)
        repository = UsersRepository(remoteDataSource, localDataSource)

        val users = repository.getUsers(false) as Result.Success

        assertThat(users.data).isEqualTo(localUserList)
    }

    @Test
    fun getUsers_requestsEmptyTaskList() = mainCoroutineRule.runBlockingTest{
        localDataSource = FakeLocalDataSource(emptyUserList)
        remoteDataSource = FakeRemoteDataSource(emptyUserList)
        repository = UsersRepository(remoteDataSource, localDataSource)

        val users = repository.getUsers(false) as Result.Success

        assertThat(users.data).isEqualTo(emptyUserList)
    }

    @Test
    fun getUsers_getError() = mainCoroutineRule.runBlockingTest{
        localDataSource = FakeLocalDataSource(null)
        remoteDataSource = FakeRemoteDataSource(null)
        repository = UsersRepository(remoteDataSource, localDataSource)

        val users = repository.getUsers(false)

        assertThat(users is Result.Success).isEqualTo(false)
        assertThat(users is Result.Error).isEqualTo(true)
    }
}