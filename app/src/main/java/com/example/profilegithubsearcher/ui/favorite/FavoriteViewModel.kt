package com.example.profilegithubsearcher.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.profilegithubsearcher.data.repository.UserRepository

class FavoriteViewModel(private val userRepos: UserRepository) : ViewModel(){
    fun getFavoriteUser() = userRepos.getAllUser()

}