package com.example.moviemood.repository

import com.example.moviemood.utils.ResultStatu
import com.example.moviemood.api.ApiService
import com.example.moviemood.model.MovieResponse
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getMoviesGenres(page: Int ,with_genres :String,token: String)=apiService.getMoviesGenres(page,with_genres,token)
    suspend fun getGenres(token: String)=apiService.getGenres(token)
    suspend fun getNowPlaying(page: Int,token:String)=apiService.getNowPlaying(page,token)
    suspend fun getTrending(time:String,token:String)=apiService.getTrending(time,token)
    suspend fun getSearchMovie(query:String,token: String)=apiService.getSearchMoviesList(query,token)
    suspend fun getDetailsMovie(id:Int,token: String)=apiService.getMovieDetails(id,token)
    suspend fun getCredits(id: Int,token: String)=apiService.getMovieCredits(id,token)
    suspend fun getMovieVideo(id: Int,token: String)=apiService.getMovieVideo(id,token)
}