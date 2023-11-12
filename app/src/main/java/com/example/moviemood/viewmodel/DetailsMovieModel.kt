package com.example.moviemood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemood.db.MoviesEntity
import com.example.moviemood.model.CreditsLisResponse
import com.example.moviemood.model.DetailsMovieResponse

import com.example.moviemood.model.VideosResponse
import com.example.moviemood.repository.ApiRepository
import com.example.moviemood.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsMovieModel @Inject constructor(val apiRepository: ApiRepository,private val databaseRepository: DatabaseRepository):ViewModel() {
    //Api
    val detailsMovie = MutableLiveData<DetailsMovieResponse>()
    val loading = MutableLiveData<Boolean>()

    fun loadDetailsMovie(id: Int,token:String) = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getDetailsMovie(id,token)
        if (response.isSuccessful) {
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }


    val movideVideo=MutableLiveData<VideosResponse>()
    fun loadMovieVideo(id: Int,token:String)=viewModelScope.launch {
        loading.postValue(true)
         val videoresponse=apiRepository.getMovieVideo(id, token)
         if(videoresponse.isSuccessful){
             movideVideo.postValue(videoresponse.body())
         }
        loading.postValue(false)
    }


    //credits
    val creditsMovie=MutableLiveData<CreditsLisResponse>()
    fun loadCreditsMovie(id: Int,token: String) = viewModelScope.launch {
        loading.postValue(true)
        val response = apiRepository.getCredits(id,token)
        if (response.isSuccessful) {
            creditsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }


    //Database
    val isFavorite = MutableLiveData<Boolean>()
    suspend fun existMovie(id:Int)= withContext(viewModelScope.coroutineContext){
        databaseRepository.existMovie(id)
    }

    fun favoriteMovie(id:Int, entity: MoviesEntity)=viewModelScope.launch {
        val exists= databaseRepository.existMovie(id)
        if (exists){
            isFavorite.postValue(false)
            databaseRepository.deleteMovie(entity)
        }else{
            isFavorite.postValue(true)
            databaseRepository.insertMovie(entity)
        }
    }

}