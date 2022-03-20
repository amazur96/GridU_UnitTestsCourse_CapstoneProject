package com.example.gridu_unittestscourse_capstoneproject.ui.users

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.gridu_unittestscourse_capstoneproject.R
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.FakeUserRepository
import com.example.gridu_unittestscourse_capstoneproject.di.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class UsersFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: FakeUserRepository

    private val users = listOf(
        UserDetails(0), UserDetails(1), UserDetails(2),
        UserDetails(3), UserDetails(4)
    )

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun usersScreen_displayDataInUi() {
        runBlocking {
            users.forEach { repository.saveUser(it) }
        }
        launchFragmentInHiltContainer<UsersFragment>()

        onView(withId(R.id.usersRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyMessageFrameLayout)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorMessageFrameLayout)).check(matches(not(isDisplayed())))
    }

    @Test
    fun usersScreen_displayEmptyDataInUi() {
        launchFragmentInHiltContainer<UsersFragment>()

        onView(withId(R.id.emptyMessageFrameLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.errorMessageFrameLayout)).check(matches(not(isDisplayed())))
    }
}