package com.example.profilegithubsearcher.di

import android.content.Context

import com.example.profilegithubsearcher.data.local.room.UserEntityRoomDatabase
import com.example.profilegithubsearcher.data.repository.UserRepository
import com.example.profilegithubsearcher.ui.preferences.SettingPreferences
import com.example.profilegithubsearcher.ui.preferences.dataStore
import com.example.profilegithubsearcher.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserEntityRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return UserRepository.getInstance(dao, appExecutors)
    }

    fun provideSettingPreferences(context: Context) = SettingPreferences.getInstance(context.dataStore)
}