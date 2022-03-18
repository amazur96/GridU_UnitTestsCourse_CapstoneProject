package com.example.gridu_unittestscourse_capstoneproject.ui.user_details

import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.gridu_unittestscourse_capstoneproject.R
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.FakeUserRepository
import com.example.gridu_unittestscourse_capstoneproject.di.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class UserDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: FakeUserRepository

    private val userDetails = UserDetails(
        0, "Test Name", "test_login",
        "Long test bio", "Test Location", "Test email",
        12, 44, "false"
    )
    private val emptyUserDetails = UserDetails(
        0, "Test Name", "test_login",
        "", "", "", 0, 0, ""
    )

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun userDetailsScreen_displayedFullDataInUi() {
        val bundle = UserDetailsFragmentArgs(userDetails.id).toBundle()
        runBlocking {
            repository.saveUser(userDetails)
        }
        launchFragmentInHiltContainer<UserDetailsFragment>(bundle)

        onView(withId(R.id.nameTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.nameTextView)).check(matches(withText("Test Name")))
        onView(withId(R.id.loginTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.loginTextView)).check(matches(withText("test_login")))
        onView(withId(R.id.bioTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.bioTextView)).check(matches(withText("Long test bio")))
        onView(withId(R.id.locationTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.locationTextView)).check(matches(withText("Test Location")))
        onView(withId(R.id.emailTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.emailTextView)).check(matches(withText("Test email")))
        onView(withId(R.id.repositoriesCounterTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.repositoriesCounterTextView)).check(matches(withText("12")))
        onView(withId(R.id.followersCounterTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.followersCounterTextView)).check(matches(withText("44")))
        onView(withId(R.id.hireableTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.hireableTextView)).check(matches(withText("false")))
        onView(withId(R.id.errorMessageFrameLayout)).check(matches(not(isDisplayed())))
    }

    @Test
    fun userDetailsScreen_displayedEmptyDataInUi() {
        val bundle = UserDetailsFragmentArgs(emptyUserDetails.id).toBundle()
        runBlocking {
            repository.saveUser(emptyUserDetails)
        }
        launchFragmentInHiltContainer<UserDetailsFragment>(bundle)

        onView(withId(R.id.nameTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.nameTextView)).check(matches(withText("Test Name")))
        onView(withId(R.id.loginTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.loginTextView)).check(matches(withText("test_login")))
        onView(withId(R.id.bioTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.bioTextView)).check(matches(withText("")))
        onView(withId(R.id.locationTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.locationTextView)).check(matches(withText(R.string.empty_location)))
        onView(withId(R.id.emailTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.emailTextView)).check(matches(withText(R.string.empty_email)))
        onView(withId(R.id.repositoriesCounterTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.repositoriesCounterTextView)).check(matches(withText("0")))
        onView(withId(R.id.followersCounterTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.followersCounterTextView)).check(matches(withText("0")))
        onView(withId(R.id.hireableTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.hireableTextView)).check(matches(withText("false")))
    }

    @Test
    fun userDetailsScreen_displayedErrorInUi() {
        val bundle = UserDetailsFragmentArgs(userDetails.id).toBundle()
        launchFragmentInHiltContainer<UserDetailsFragment>(bundle)

        onView(withId(R.id.errorMessageFrameLayout)).check(matches(isDisplayed()))
    }
}