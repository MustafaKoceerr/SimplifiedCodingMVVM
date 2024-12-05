package com.kocerlabs.simplifiedcodingmvvm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.kocerlabs.simplifiedcodingmvvm.MyApplication
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginRequest
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponse
import com.kocerlabs.simplifiedcodingmvvm.data.repository.AuthRepository
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : BaseViewModel(repository) {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>() // Replace ResponseType with your actual data type.
    val loginResponse: LiveData<Resource<LoginResponse>> = _loginResponse // Publicly exposed as LiveData for observers.


    fun login(email: String, password: String) =
        viewModelScope.launch {
            _loginResponse.value = repository.login(LoginRequest(email, password))
        }


    suspend fun saveAuthToken(token:String) = repository.saveAuthToken(token)


    // Define ViewModel factory in a companion object
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(
                    extras[APPLICATION_KEY]
                )
                return AuthViewModel(
                    (application as MyApplication).repository
                ) as T
            }
        }
    }
}