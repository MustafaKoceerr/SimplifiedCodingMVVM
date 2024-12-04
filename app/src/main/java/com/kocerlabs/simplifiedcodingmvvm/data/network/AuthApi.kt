package com.kocerlabs.simplifiedcodingmvvm.data.network

import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginRequest
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("user/login")
    suspend fun login( // async olarak coroutine ile çağırmak için suspend yaptım.
        @Body loginRequest: LoginRequest
    ): LoginResponse

//    val URL = "https://dummyjson.com/"
}