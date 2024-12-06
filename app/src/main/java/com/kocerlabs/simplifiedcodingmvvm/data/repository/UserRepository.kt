package com.kocerlabs.simplifiedcodingmvvm.data.repository

import com.kocerlabs.simplifiedcodingmvvm.data.network.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi,
) : BaseRepository() {


    suspend fun getUser() = safeApiCall {
        api.getUser()
    }
}