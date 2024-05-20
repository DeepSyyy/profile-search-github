package com.example.profilegithubsearcher.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.profilegithubsearcher.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(user: UserEntity)

    @Query("DELETE FROM UserEntity WHERE login = :login")
    fun removeFavorite(login: String)

    @Query("SELECT * FROM UserEntity ORDER BY login ASC")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE login = :login")
    fun getUserById(login: String): LiveData<UserEntity>

}