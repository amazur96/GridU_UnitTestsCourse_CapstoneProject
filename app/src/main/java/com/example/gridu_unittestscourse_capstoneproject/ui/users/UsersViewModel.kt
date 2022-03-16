package com.example.gridu_unittestscourse_capstoneproject.ui.users

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
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepositoryContract
) : ViewModel() {
    val userList = MutableLiveData<List<UserDetails>?>()
    val loading = MutableLiveData<Boolean>()
    val emptyResponse = MutableLiveData<Boolean>()
    val error = MutableLiveData<Exception>()

    fun getUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            when (val usersDetails = usersRepository.getUsers(false)) {
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