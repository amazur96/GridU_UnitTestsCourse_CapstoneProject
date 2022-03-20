package com.example.gridu_unittestscourse_capstoneproject.ui.user_details

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
class UserDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: UsersRepository
    private lateinit var viewModel: UserDetailsViewModel

    private val userDetails = UserDetails(
        0, "Test Name", "test_login",
        "Long test bio", "Test Location", "Test email",
        12, 44, "false"
    )
    private val emptyUserDetails = UserDetails(0)
    private val error = Exception("Fail...")

    @Before
    fun init() {
        repository = mockk()
    }

    @Test
    fun getUserDetails_returnSuccessData() {
        every { runBlocking { repository.getUserDetails(userDetails.id) } } returns Result.Success(userDetails)

        viewModel = UserDetailsViewModel(repository)
        viewModel.getUserDetails(userDetails.id)

        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(true)
        assertThat(viewModel.userDetails.getOrAwaitValue()).isEqualTo(userDetails)
        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun getUserDetails_returnEmptyData() {
        every { runBlocking { repository.getUserDetails(emptyUserDetails.id) } } returns Result.Success(emptyUserDetails)

        viewModel = UserDetailsViewModel(repository)
        viewModel.getUserDetails(emptyUserDetails.id)

        assertThat(viewModel.userDetails.getOrAwaitValue()).isEqualTo(emptyUserDetails)
    }

    @Test
    fun getUserDetails_returnError() {
        every { runBlocking { repository.getUserDetails(44) } } returns Result.Error(error)

        viewModel = UserDetailsViewModel(repository)
        viewModel.getUserDetails(44)

        assertThat(viewModel.error.getOrAwaitValue()).isEqualTo(error)
    }
}