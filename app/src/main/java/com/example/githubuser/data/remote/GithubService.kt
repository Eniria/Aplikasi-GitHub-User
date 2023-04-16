package com.example.githubuser.data.remote

import com.example.githubuser.data.model.ResponseDetailUser
import com.example.githubuser.data.model.ResponsesUserGithub
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface GithubService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub():MutableList<ResponsesUserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(@Path("username") username: String): ResponseDetailUser

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowersUserGithub(@Path("username") username: String):MutableList<ResponsesUserGithub.Item>



    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowingUserGithub(@Path("username") username: String):MutableList<ResponsesUserGithub.Item>
}