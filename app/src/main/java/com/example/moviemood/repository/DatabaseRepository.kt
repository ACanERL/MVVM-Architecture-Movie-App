package com.example.moviemood.repository

import com.example.moviemood.db.MoviesDao
import com.example.moviemood.db.MoviesEntity
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val dao:MoviesDao) {
    fun getAllFavoriteList ()=dao.getAllMovies()
    suspend fun insertMovie(entity: MoviesEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MoviesEntity) = dao.deleteMovie(entity)
    suspend fun existMovie(id: Int) = dao.existMovie(id)
}