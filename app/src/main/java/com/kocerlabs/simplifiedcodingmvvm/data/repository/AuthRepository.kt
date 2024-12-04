package com.kocerlabs.simplifiedcodingmvvm.data.repository

import com.kocerlabs.simplifiedcodingmvvm.data.UserPreferences
import com.kocerlabs.simplifiedcodingmvvm.data.network.AuthApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginRequest

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(loginBody: LoginRequest) = safeApiCall({ api.login(loginBody) })

    suspend fun saveAuthToken(token:String){
        preferences.saveAuthToken(token)
        // şimdi view model'den bu fonksiyonu çağırabiliriz.
    }

}