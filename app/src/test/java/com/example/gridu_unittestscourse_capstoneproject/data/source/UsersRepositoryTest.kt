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

    private val localUserList = mutableListOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
    )
    private val remoteUserList = mutableListOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
        UserDetails(3), UserDetails(4)
    )
    private val emptyUserList = mutableListOf<UserDetails>()

    @Test
    fun getUsers_returnSuccessUserDetailsListFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        val users = getUsersRepository().getUsers(true) as? Result.Success

        assertThat(users).isNotNull()
        users?.let { assertThat(it.data).isEqualTo(remoteUserList) }
    }

    @Test
    fun getUsers_returnSuccessUserDetailsListFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        val users = getUsersRepository().getUsers(false) as? Result.Success

        assertThat(users).isNotNull()
        users?.let { assertThat(it.data).isEqualTo(localUserList) }
    }

    @Test
    fun getUsers_returnEmptyUserDetailsList() = mainCoroutineRule.runBlockingTest {
        val users = getUsersRepositoryWithEmptyData().getUsers(false) as? Result.Success

        assertThat(users).isNotNull()
        assertThat(users?.data).isEqualTo(emptyUserList)
    }

    @Test
    fun getUsers_returnError() = mainCoroutineRule.runBlockingTest {
        val users = getUsersRepositoryWithNullData().getUsers(false)

        assertThat(users is Result.Success).isEqualTo(false)
        assertThat(users is Result.Error).isEqualTo(true)
    }

    @Test
    fun getUserDetails_returnSuccessDataFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        val requestedUserId = 1
        val userDetails = getUsersRepository().getUserDetails(requestedUserId) as? Result.Success

        assertThat(userDetails).isNotNull()
        assertThat(userDetails?.data?.id).isEqualTo(requestedUserId)
    }

    @Test
    fun getUserDetails_requestWrongUserId_returnError() = mainCoroutineRule.runBlockingTest {
        val userDetails = getUsersRepository().getUserDetails(44)

        assertThat(userDetails is Result.Success).isEqualTo(false)
        assertThat(userDetails is Result.Error).isEqualTo(true)
    }

    @Test
    fun getUserDetails_returnEmptyUserDetailsList() = mainCoroutineRule.runBlockingTest {
        val userDetails = getUsersRepositoryWithEmptyData().getUserDetails(1)

        assertThat(userDetails is Result.Success).isEqualTo(false)
        assertThat(userDetails is Result.Error).isEqualTo(true)
    }

    @Test
    fun getUserDetails_returnError() = mainCoroutineRule.runBlockingTest {
        val userDetails = getUsersRepositoryWithNullData().getUserDetails(1)

        assertThat(userDetails is Result.Success).isEqualTo(false)
        assertThat(userDetails is Result.Error).isEqualTo(true)
    }

    private fun getUsersRepository() = UsersRepository(
        FakeRemoteDataSource(remoteUserList), FakeLocalDataSource(localUserList)
    )

    private fun getUsersRepositoryWithEmptyData() = UsersRepository(
        FakeRemoteDataSource(emptyUserList), FakeLocalDataSource(emptyUserList)
    )

    private fun getUsersRepositoryWithNullData() = UsersRepository(
        FakeRemoteDataSource(null), FakeLocalDataSource(null)
    )
}