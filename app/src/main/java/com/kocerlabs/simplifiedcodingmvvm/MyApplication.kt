package com.kocerlabs.simplifiedcodingmvvm

import android.app.Application
import com.kocerlabs.simplifiedcodingmvvm.data.UserPreferences
import com.kocerlabs.simplifiedcodingmvvm.data.network.AuthApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.UserApi
import com.kocerlabs.simplifiedcodingmvvm.data.network.model.RemoteDataSource
import com.kocerlabs.simplifiedcodingmvvm.data.repository.AuthRepository
import com.kocerlabs.simplifiedcodingmvvm.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MyApplication : Application() {
    val userPreferences: UserPreferences by lazy { UserPreferences(applicationContext) }
    val repository: AuthRepository by lazy {
        val api = RemoteDataSource().buildApi(AuthApi::class.java)
        AuthRepository(api, userPreferences)
    }


    val userRepository: UserRepository by lazy {
        val token = runBlocking { userPreferences.authToken.first() } // Flow'dan token alınır
        val api = RemoteDataSource().buildApi(UserApi::class.java, token)
        UserRepository(api)
    }
    companion object {
        lateinit var appInstance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this@MyApplication
    }

}