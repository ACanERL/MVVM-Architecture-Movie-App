package com.example.moviemood.api

import com.example.moviemood.model.CommonMoviesListResponse
import com.example.moviemood.model.CreditsLisResponse
import com.example.moviemood.model.DetailsMovieResponse
import com.example.moviemood.model.GenreListResponse
import com.example.moviemood.model.MovieResponse
import com.example.moviemood.model.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page")page:Int,
        @Header("Authorization") token:String
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(@Header("Authorization") token:String): Response<GenreListResponse>

    @GET("discover/movie")
    suspend fun getMoviesGenres(
        @Query("page") page: Int,
        @Query("with_genres") with_genres: String,
        @Header("Authorization") token:String
    ): Response<CommonMoviesListResponse>

    @GET("trending/movie/day")
    suspend fun getTrending(
        @Query("time_window") time:String,
        @Header("Authorization") token:String
    ):Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMoviesList(
        @Query("query") query: String,
        @Header("Authorization") token:String): Response<CommonMoviesListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Header("Authorization") token:String
    ): Response<DetailsMovieResponse>



    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") id: Int,
        @Header("Authorization") token:String): Response<CreditsLisResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") id: Int,
        @Header("Authorization") token:String
    ): Response<VideosResponse>
}