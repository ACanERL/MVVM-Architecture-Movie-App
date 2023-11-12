package com.example.moviemood.di

import android.content.Context
import androidx.room.Room
import com.example.moviemood.db.MoviesDatabase
import com.example.moviemood.db.MoviesEntity
import com.example.moviemood.utils.MOVIES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MoviesDatabase::class.java, MOVIES_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    @Provides
    @Singleton
    fun provideDao(db : MoviesDatabase) = db.moviesDoa()


    @Provides
    @Singleton
    fun provideEntity() = MoviesEntity()
}