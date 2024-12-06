package com.kocerlabs.simplifiedcodingmvvm.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // create datastore Instance


// datastore works with Kotlin coroutine and flow
    // bu yüzden değeri getirmek için flow'a ihtiyacımız var

    val authToken: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH] ?: ""
        }

    suspend fun saveAuthToken(authToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    companion object {
        // At the top level of your kotlin file:
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")
        private val KEY_AUTH = stringPreferencesKey("my_data_store")
    }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}