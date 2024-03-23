package com.example.profilegithubsearcher.data.retrofit

import com.example.profilegithubsearcher.data.repsonse.DetailUserResponse
import com.example.profilegithubsearcher.data.repsonse.User
import com.example.profilegithubsearcher.data.repsonse.UserSearchResponse
import com.example.profilegithubsearcher.util.Utils.Companion.TOKEN
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun getUsers(
        @Query("q") q: String
    ): Call<UserSearchResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}