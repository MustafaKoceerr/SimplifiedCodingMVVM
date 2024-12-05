package com.kocerlabs.simplifiedcodingmvvm.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext

    // create datastore Instance


// datastore works with Kotlin coroutine and flow
    // bu yüzden değeri getirmek için flow'a ihtiyacımız var

    val authToken: Flow<String>
        get() = applicationContext.dataStore.data.map { preferences ->
            preferences[KEY_AUTH] ?: ""
        }

    suspend fun saveAuthToken(authToken: String) {
        applicationContext.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    companion object {
        // At the top level of your kotlin file:
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")
        private val KEY_AUTH = stringPreferencesKey("my_data_store")
    }

    suspend fun clear(){
        applicationContext.dataStore.edit { preferences->
            preferences.clear()
        }
    }
}