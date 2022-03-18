package com.example.gridu_unittestscourse_capstoneproject.ui.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.UsersRepository
import com.example.gridu_unittestscourse_capstoneproject.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: UsersRepository
    private lateinit var viewModel: UsersViewModel

    private val users = listOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
        UserDetails(3), UserDetails(4)
    )
    private val error = Exception("Fail...")

    @Before
    fun init() {
        repository = mockk()
    }

    @Test
    fun getUsers_requestSuccessData() {
        every {
            runBlocking { repository.getUsers(false) }
        } returns Result.Success(users)

        viewModel = UsersViewModel(repository).apply {
            getUsers()
        }

        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(true)
        assertThat(viewModel.userList.getOrAwaitValue()).isEqualTo(users)
        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun getUsers_requestEmptyData() {
        every {
            runBlocking { repository.getUsers(false) }
        } returns Result.Success(listOf())

        viewModel = UsersViewModel(repository).apply {
            getUsers()
        }

        assertThat(viewModel.emptyResponse.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun getUsers_requestErrorData() {
        every {
            runBlocking { repository.getUsers(false) }
        } returns Result.Error(error)

        viewModel = UsersViewModel(repository).apply {
            getUsers()
        }

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(error)
    }
}