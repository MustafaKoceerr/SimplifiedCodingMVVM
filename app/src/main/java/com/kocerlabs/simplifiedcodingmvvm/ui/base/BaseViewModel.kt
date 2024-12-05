package com.kocerlabs.simplifiedcodingmvvm.ui.base

import androidx.lifecycle.ViewModel
import com.kocerlabs.simplifiedcodingmvvm.data.network.UserApi
import com.kocerlabs.simplifiedcodingmvvm.data.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

    suspend fun logout(api: UserApi) = repository.logout(api)


}