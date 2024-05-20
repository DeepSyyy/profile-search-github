package com.example.profilegithubsearcher.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profilegithubsearcher.data.repository.UserRepository
import com.example.profilegithubsearcher.di.Injection
import com.example.profilegithubsearcher.ui.detail.DetailViewModel
import com.example.profilegithubsearcher.ui.favorite.FavoriteViewModel
import com.example.profilegithubsearcher.ui.home.MainViewModel
import com.example.profilegithubsearcher.ui.preferences.PreferencesViewModel
import com.example.profilegithubsearcher.ui.preferences.SettingPreferences

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val pref: SettingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideSettingPreferences(context)
                )
            }.also { instance = it }
    }
}