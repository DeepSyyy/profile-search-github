package com.example.profilegithubsearcher.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.profilegithubsearcher.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 3 , exportSchema = false)
abstract class UserEntityRoomDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserEntityRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserEntityRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserEntityRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserEntityRoomDatabase::class.java, "favorite_user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as UserEntityRoomDatabase
        }
    }
}