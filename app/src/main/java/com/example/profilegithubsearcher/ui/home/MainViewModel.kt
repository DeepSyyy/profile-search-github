package com.example.profilegithubsearcher.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.data.repsonse.UserSearchResponse
import com.example.profilegithubsearcher.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainViewModel: ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    init {
        searchUser("sasuke")
    }

    fun searchUser(q: String) {
        Log.d(TAG, "Ini tag")
        _isLoading.value = true

        ApiConfig.getApiService().getUsers( q).apply {
            enqueue(object : Callback<UserSearchResponse> {
                override fun onResponse(call: Call<UserSearchResponse>, response: Response<UserSearchResponse>) {
                    Log.d(TAG, "response: ${response.body().toString()}")
                    if (response.isSuccessful) {
                        _listUser.value = response.body()?.items
                        Log.d(TAG, response.body()?.items.toString())
                    }else Log.e(TAG, response.message())

                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                    Log.e(TAG, "eData: ${t.message.toString()}")
                    _isError.postValue(true)
                    _isLoading.postValue(true)
                }
            })
        }
    }

    companion object {
       private val TAG = MainViewModel::class.java.simpleName
    }
}