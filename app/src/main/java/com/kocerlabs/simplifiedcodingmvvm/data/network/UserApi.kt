package com.kocerlabs.simplifiedcodingmvvm.data.network

import com.kocerlabs.simplifiedcodingmvvm.data.network.model.LoginResponseSecond
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi{

    @GET("user/me")
    suspend fun getUser(): LoginResponseSecond

    companion object{
        val URL = "https://dummyjson.com/"
    }


    @POST("logout")
    suspend fun logout(): ResponseBody
    // Eğer döndürülen değerle ilgilenmiyorsan, responseBody döndürebilirsin.

}