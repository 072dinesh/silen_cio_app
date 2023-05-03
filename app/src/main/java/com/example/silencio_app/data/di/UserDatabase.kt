package com.example.silencio_app.data.di

import android.content.Context

import androidx.room.Room

import com.example.silencio_app.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppRoomModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "silencio-database"
    )
        .fallbackToDestructiveMigration()
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()

    @Singleton
    @Provides
    fun provideUserDao(
        database: AppDatabase
    ) = database.userDao()
}

