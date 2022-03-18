package com.example.gridu_unittestscourse_capstoneproject.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class UsersDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    private val users = listOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
        UserDetails(3), UserDetails(4)
    )
    private val userDetails = UserDetails(
        0, "Test Name", "test_login",
        "Long test bio", "Test Location", "Test email",
        12, 44, "false"
    )
    private val updatedUserDetails = UserDetails(
        0, "Updated Test Name", "updated_test_login",
        "Updated long test bio", "Test Location", "Test email",
        12, 44, "false"
    )
    private val emptyUserDetails = UserDetails(0)

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
    }

    @Test
    fun insertUserIntoDbAndGetById() = runBlocking {
        database.usersDao().insertUser(userDetails)

        val loaded = database.usersDao().getUserById(userDetails.id)

        assertThat(loaded).isNotNull()
        assertThat(loaded is UserDetails).isEqualTo(true)
        assertThat(loaded?.id).isEqualTo(userDetails.id)
        assertThat(loaded?.name).isEqualTo(userDetails.name)
        assertThat(loaded?.login).isEqualTo(userDetails.login)
        assertThat(loaded?.bio).isEqualTo(userDetails.bio)
    }

    @Test
    fun insertUserIntoDbUpdateAndGetById() = runBlocking {
        database.usersDao().insertUser(userDetails)

        database.usersDao().updateUser(updatedUserDetails)
        val loaded = database.usersDao().getUserById(updatedUserDetails.id)

        assertThat(loaded).isNotNull()
        assertThat(loaded is UserDetails).isEqualTo(true)
        assertThat(loaded?.id).isEqualTo(updatedUserDetails.id)
        assertThat(loaded?.name).isEqualTo(updatedUserDetails.name)
        assertThat(loaded?.login).isEqualTo(updatedUserDetails.login)
        assertThat(loaded?.bio).isEqualTo(updatedUserDetails.bio)
    }

    @Test
    fun insertUserIntoDbDeleteAndGetById() = runBlocking {
        database.usersDao().insertUser(userDetails)

        database.usersDao().deleteUser(userDetails)
        val loaded = database.usersDao().getUserById(userDetails.id)

        assertThat(loaded).isNull()
    }

    @Test
    fun insertUserListIntoDbAndGetUsers() = runBlocking {
        users.forEach {
            database.usersDao().insertUser(it)
        }

        val loadedUsers = database.usersDao().getUsers()

        assertThat(loadedUsers).isNotNull()
        assertThat(loadedUsers.size).isEqualTo(users.size)
    }

    @After
    fun closeDb() = database.close()
}