package com.example.moviemood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemood.model.CommonMoviesListResponse
import com.example.moviemood.model.GenreListResponse
import com.example.moviemood.utils.ResultStatu
import com.example.moviemood.model.MovieResponse
import com.example.moviemood.model.Result
import com.example.moviemood.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(private val apiRepository: ApiRepository):ViewModel() {
    val emptyList = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    val genreList = MutableLiveData<GenreListResponse>()
    fun loadGenreList(token: String) = viewModelScope.launch {
        val response = apiRepository.getGenres(token)
        if (response.isSuccessful) {
            genreList.postValue(response.body())
        }
    }


    val genreMoviesList = MutableLiveData<CommonMoviesListResponse>()
    fun loadGenreMoviesList(with_genres :String,token: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getMoviesGenres(1,with_genres,token)
        if (response.isSuccessful) {
            genreMoviesList.postValue(response.body())
        }
        loading.postValue(false)
    }

    val nowPlayingList = MutableLiveData<MovieResponse>()
    fun getNowPlaying(page:Int,token: String)=viewModelScope.launch {
        loading.postValue(true)
        val response=apiRepository.getNowPlaying(page,token)
        if(response.isSuccessful){
            nowPlayingList.postValue(response.body())
        }
        loading.postValue(false)
    }
    val trendingList= MutableLiveData<MovieResponse>()
    fun getTrending(time:String,token:String)=viewModelScope.launch {
        loading.postValue(true)
        val response=apiRepository.getTrending(time, token)
        if(response.isSuccessful){
            trendingList.postValue(response.body())
        }
        loading.postValue(false)
    }


   val searchList=MutableLiveData<CommonMoviesListResponse>()
    fun getSearch(query:String,token: String)=viewModelScope.launch {
        loading.postValue(true)
        val responseSearch=apiRepository.getSearchMovie(query,token)
        if(responseSearch.isSuccessful){
            searchList.postValue(responseSearch.body())
            emptyList.postValue(false)
        }else{
            emptyList.postValue(true)
        }
        loading.postValue(false)
    }

}