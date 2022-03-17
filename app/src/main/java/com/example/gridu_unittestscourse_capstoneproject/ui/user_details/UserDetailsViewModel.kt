package com.example.gridu_unittestscourse_capstoneproject.ui.user_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.UsersRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepositoryContract
) : ViewModel() {
    val userDetails = MutableLiveData<UserDetails?>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Exception>()

    fun getUserDetails(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            when(val response = usersRepository.getUserDetails(userId)) {
                is Result.Success -> userDetails.postValue(response.data)
                is Result.Error -> error.postValue(response.exception)
            }
            loading.postValue(false)
        }
    }
}