package com.example.profilegithubsearcher.ui.following

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.data.retrofit.ApiConfig
import com.example.profilegithubsearcher.ui.follower.FollowerViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: MutableLiveData<Boolean> = _isError

    private val _listFollower = MutableLiveData<ArrayList<User>>()
    val listFollower: MutableLiveData<ArrayList<User>> = _listFollower

    fun fetchFollowing(q: String){
        _isLoading.value = true
        ApiConfig.getApiService().getFollowing(q).apply {
            enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>){
                    if(response.isSuccessful){
                        _listFollower.value = response.body()
                    }else Log.e(TAG, response.message())

                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable){
                    Log.d(TAG, "eData: ${t.message.toString()}")
                    _isError.postValue(true)
                    _isLoading.postValue(false)
                }
            })
        }
    }

    companion object {
        private val TAG = FollowerViewModel::class.java.simpleName
    }
}