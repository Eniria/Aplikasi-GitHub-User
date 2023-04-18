package com.example.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.model.ResponsesUserGithub

@Dao
interface UserDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ResponsesUserGithub.Item)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<ResponsesUserGithub.Item>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id:Int): ResponsesUserGithub.Item

    @Delete
    fun delete(User: ResponsesUserGithub.Item)
}