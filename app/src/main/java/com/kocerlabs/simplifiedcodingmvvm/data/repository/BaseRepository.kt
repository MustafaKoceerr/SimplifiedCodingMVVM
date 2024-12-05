package com.kocerlabs.simplifiedcodingmvvm.data.repository

import com.kocerlabs.simplifiedcodingmvvm.data.network.Resource
import com.kocerlabs.simplifiedcodingmvvm.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository (){


    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) { //api call'unu IO dispatcher'da gerçekleştirmek istediğim için
            try {
                Resource.Success(apiCall.invoke()) // invoke ile api call'u çalıştırıyoruz.
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        Resource.Failure<Nothing>(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else->{
                        Resource.Failure<Nothing>(true, null, null)
                    }
                }
            }
        }
    }



    suspend fun logout(api: UserApi) = safeApiCall { api.logout() }
}