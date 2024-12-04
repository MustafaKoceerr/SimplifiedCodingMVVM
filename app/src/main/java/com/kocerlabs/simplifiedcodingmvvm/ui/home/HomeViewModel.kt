package com.kocerlabs.simplifiedcodingmvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kocerlabs.simplifiedcodingmvvm.MyApplication
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponseSecond
import com.kocerlabs.simplifiedcodingmvvm.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _user: MutableLiveData<Resource<LoginResponseSecond>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponseSecond>>
        get() = _user


    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()

    }

    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application = checkNotNull(
                    extras[APPLICATION_KEY]
                )
                return HomeViewModel(
                    (application as MyApplication).userRepository
                ) as T
            }
        }
    }
}