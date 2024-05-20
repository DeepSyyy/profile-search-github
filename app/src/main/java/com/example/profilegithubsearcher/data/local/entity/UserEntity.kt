package com.example.profilegithubsearcher.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class UserEntity(
    @PrimaryKey
    var login: String = "",
    var avatarUrl: String? = null,

    var isFavorite: Boolean = false,
)