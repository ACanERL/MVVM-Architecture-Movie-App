package com.example.moviemood.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviemood.utils.MOVIE_TABLE

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertMovie(entity: MoviesEntity)

    @Delete
    suspend fun deleteMovie(entity: MoviesEntity)

    @Query("SELECT * From ${MOVIE_TABLE}")
    fun getAllMovies() : MutableList<MoviesEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${MOVIE_TABLE} WHERE id = :id)")
    suspend fun existMovie(id:Int) : Boolean
}