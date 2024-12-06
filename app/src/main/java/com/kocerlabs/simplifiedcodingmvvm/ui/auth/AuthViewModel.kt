package com.kocerlabs.simplifiedcodingmvvm.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginRequest
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponse
import com.kocerlabs.simplifiedcodingmvvm.data.repository.AuthRepository
import com.kocerlabs.simplifiedcodingmvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
) : BaseViewModel(repository) {
    private val _loginResponse =
        MutableLiveData<Resource<LoginResponse>>() // Replace ResponseType with your actual data type.
    val loginResponse: LiveData<Resource<LoginResponse>> =
        _loginResponse // Publicly exposed as LiveData for observers.


    fun login(email: String, password: String) =
        viewModelScope.launch {
            _loginResponse.value = repository.login(LoginRequest(email, password))
        }


    suspend fun saveAuthToken(token: String) = repository.saveAuthToken(token)


}