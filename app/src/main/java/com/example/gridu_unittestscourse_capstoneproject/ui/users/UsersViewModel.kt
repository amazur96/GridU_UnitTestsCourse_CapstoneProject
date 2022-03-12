package com.example.gridu_unittestscourse_capstoneproject.ui.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gridu_unittestscourse_capstoneproject.data.Result
import com.example.gridu_unittestscourse_capstoneproject.data.model.UserDetails
import com.example.gridu_unittestscourse_capstoneproject.data.source.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    val userList = MutableLiveData<List<UserDetails>?>()
    val loading = MutableLiveData<Boolean>()
    val emptyResponse = MutableLiveData<Boolean>()
    val error = MutableLiveData<Exception>()

    fun getUsers() {
        viewModelScope.launch {
            loading.postValue(true)
            when (val usersDetails = usersRepository.getUsers()) {
                is Result.Success -> {
                    if (usersDetails.data.isNullOrEmpty()) {
                        emptyResponse.postValue(true)
                    } else {
                        userList.postValue(usersDetails.data)
                    }
                    loading.postValue(false)
                }
                is Result.Error -> {
                    error.postValue(usersDetails.exception)
                    loading.postValue(false)
                }
            }
        }
    }
}