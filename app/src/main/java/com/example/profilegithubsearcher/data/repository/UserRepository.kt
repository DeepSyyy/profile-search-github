package com.example.profilegithubsearcher.data.repository


import androidx.lifecycle.LiveData
import com.example.profilegithubsearcher.data.local.entity.UserEntity
import com.example.profilegithubsearcher.data.local.room.UserDao
import com.example.profilegithubsearcher.util.AppExecutors

class UserRepository private constructor(
    private val newsDao: UserDao,
    private val appExecutors: AppExecutors
) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao, appExecutors)
            }.also { instance = it }
    }

    fun insertFavorite(user: UserEntity) {
        appExecutors.diskIO.execute {
            newsDao.insertFavorite(user)
        }
    }

    fun removeFavorite(login: String) {
        appExecutors.diskIO.execute {
            newsDao.removeFavorite(login)
        }
    }

    fun getAllUser(): LiveData<List<UserEntity>> {
        return newsDao.getAllUser()
    }

    fun getUserById(login: String): LiveData<UserEntity> {
        return newsDao.getUserById(login)
    }
}