package com.example.silencio_app.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.silencio_app.ditailsfragment.auth.UserModel

@Database(entities = [UserModel::class],version=1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
}