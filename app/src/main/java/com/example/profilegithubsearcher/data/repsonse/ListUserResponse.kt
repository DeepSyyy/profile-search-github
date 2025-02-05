package com.example.profilegithubsearcher.data.repsonse

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

    @field:SerializedName("items")
    val items: ArrayList<User>

)

data class User(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    )
