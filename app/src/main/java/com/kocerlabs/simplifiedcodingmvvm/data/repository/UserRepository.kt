package com.kocerlabs.simplifiedcodingmvvm.data.repository

import com.kocerlabs.simplifiedcodingmvvm.data.UserPreferences
import com.kocerlabs.simplifiedcodingmvvm.data.network.AuthApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.UserApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginRequest

class UserRepository(
    private val api: UserApi,
) : BaseRepository() {


    suspend fun getUser() = safeApiCall {
        api.getUser()
    }
}