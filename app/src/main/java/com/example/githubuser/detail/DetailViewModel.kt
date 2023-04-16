package com.example.githubuser.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.ApiClient
import com.example.githubuser.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowersUser = MutableLiveData<Result>()
    val resultFollowingUser = MutableLiveData<Result>()
    fun getDetailUser(username: String){
        viewModelScope.launch {
            flow{
                val response = ApiClient
                    .githubService
                    .getDetailUserGithub(username)

                emit(response)
            }.onStart {//dijalankan ketika mulai
                resultDetailUser.value= Result.Loading(true)
            }.onCompletion {//dijalankan ketika selesai
                resultDetailUser.value= Result.Loading(false)
            }.catch { //dijalankan ketika error
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect{
                resultDetailUser.value =  Result.Success(it)
            }
        }
    }

    fun getFollowers(username: String){
        viewModelScope.launch {
            flow{
                val response = ApiClient
                    .githubService
                    .getFollowersUserGithub(username)

                emit(response)
            }.onStart {//dijalankan ketika mulai
                resultFollowersUser.value= Result.Loading(true)
            }.onCompletion {//dijalankan ketika selesai
                resultFollowersUser.value= Result.Loading(false)
            }.catch { //dijalankan ketika error
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowersUser.value = Result.Error(it)
            }.collect{
                resultFollowersUser.value =  Result.Success(it)
            }
        }
    }

    fun getFollowing(username: String){
        viewModelScope.launch {
            flow{
                val response = ApiClient
                    .githubService
                    .getFollowingUserGithub(username)

                emit(response)
            }.onStart {//dijalankan ketika mulai
                resultFollowingUser.value= Result.Loading(true)
            }.onCompletion {//dijalankan ketika selesai
                resultFollowingUser.value= Result.Loading(false)
            }.catch { //dijalankan ketika error
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowingUser.value = Result.Error(it)
            }.collect{
                resultFollowingUser.value =  Result.Success(it)
            }
        }
    }
}