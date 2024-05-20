package com.example.profilegithubsearcher.ui.detail


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profilegithubsearcher.data.local.entity.UserEntity
import com.example.profilegithubsearcher.data.repository.UserRepository
import com.example.profilegithubsearcher.data.repsonse.DetailUserResponse
import com.example.profilegithubsearcher.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val userRepo: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _userResponse = MutableLiveData<DetailUserResponse>()
    val userResponse: LiveData<DetailUserResponse> = _userResponse

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite


    fun getUserDetail(id: String) {
        Log.d(TAG, "response: ini tag detail")
        _isLoading.value = true

        ApiConfig.getApiService().getUser(username = id).apply {
            enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {

                    if (response.isSuccessful) {
                        _userResponse.value = response.body()
                    } else Log.e(TAG, response.message())

                    _isLoading.value = false
                    _isError.value = false
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "eData: ${t.message.toString()}")
                    _isError.postValue(true)
                    _isLoading.postValue(true)
                }
            })
        }
    }

    fun saveAsFavUser(user: UserEntity) {
        userRepo.insertFavorite(user)
    }

    fun removeFavUser(login: String) {
        userRepo.removeFavorite(login)
    }

    fun getUserById(login: String, lifecycleOwner: LifecycleOwner) {
        userRepo.getUserById(login).observe(lifecycleOwner) {
            _isFavorite.value = it != null
        }
    }


    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }
}