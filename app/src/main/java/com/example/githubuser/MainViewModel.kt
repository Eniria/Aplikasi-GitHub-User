package com.example.githubuser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.ApiClient
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val resultUser = MutableLiveData<com.example.githubuser.utils.Result>()

    fun getUser(){
        viewModelScope.launch {
                flow{
                    val response = ApiClient
                        .githubService
                        .getUserGithub()

                    emit(response)
                }.onStart {//dijalankan ketika mulai
                    resultUser.value= com.example.githubuser.utils.Result.Loading(true)
                }.onCompletion {//dijalankan ketika selesai
                    resultUser.value= com.example.githubuser.utils.Result.Loading(false)
                }.catch { //dijalankan ketika error
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultUser.value = com.example.githubuser.utils.Result.Error(it)
                }.collect{
                    resultUser.value = com.example.githubuser.utils.Result.Success(it)
                }
        }
    }
}