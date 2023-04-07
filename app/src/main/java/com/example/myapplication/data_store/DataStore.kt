package com.example.myapplication.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(context: Context) {

    private val dataStore = context.createDataStore(name = "user_data")

    /** Create some keys we will use them to store and retrieve the data */
    companion object {
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")  /** define key type to store value */
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
    }

    /** Store user data
    // refer to the data store and using edit
    // we can store values using the keys */
    suspend fun storeUser(age: Int, name: String) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
            /** here it refers to the preferences we are editing */
        }
    }

    /** Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine */
    val userAgeFlow: Flow<Int> = dataStore.data.map {
        it[USER_AGE_KEY] ?: 0
    }

    /** Create a name flow to retrieve name from the preferences */
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }
}