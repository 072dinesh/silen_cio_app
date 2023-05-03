package com.example.silencio_app.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.silencio_app.ditailsfragment.auth.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
     fun getUser():LiveData<List<UserModel>>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert(userModel: UserModel)



}