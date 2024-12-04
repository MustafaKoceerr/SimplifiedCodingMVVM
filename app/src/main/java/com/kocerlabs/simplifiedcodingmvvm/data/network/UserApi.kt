package com.kocerlabs.simplifiedcodingmvvm.data.network

import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponseSecond
import retrofit2.http.GET

interface UserApi{

    @GET("user/me")
    suspend fun getUser(): LoginResponseSecond

    companion object{
        val URL = "https://dummyjson.com/"
    }

}