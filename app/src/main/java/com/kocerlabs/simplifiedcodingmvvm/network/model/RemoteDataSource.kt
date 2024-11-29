package com.kocerlabs.simplifiedcodingmvvm.network.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    companion object {
        private const val BASE_URL = "https://dummyjson.com/"
    }

    fun <Api> buildApi(
        api: Class<Api>
    ): Api { // retrofit nesnesini yaratacak olan generic fonksiyon
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}