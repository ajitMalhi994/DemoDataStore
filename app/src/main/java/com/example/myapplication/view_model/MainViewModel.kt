package com.example.myapplication.view_model

import androidx.lifecycle.*
import com.example.myapplication.data_store.DataStore
import kotlinx.coroutines.launch

class MainViewModel (private val dataStore: DataStore ) : ViewModel() {

    val name = MutableLiveData<String>()
    val age = MutableLiveData<Int>()

    fun saveUser(name: String, age: Int){ /** Store user by using data store */

        viewModelScope.launch {

            dataStore.storeUser(age, name)

        }

    }


    fun retrieveSavedUser(context: LifecycleOwner){ /** Fetch the saved user */

        dataStore.apply {

            userNameFlow.asLiveData().observe(context){
                name.postValue(it)
            }

            userAgeFlow.asLiveData().observe(context){
                age.postValue(it)
            }
        }

    }

}