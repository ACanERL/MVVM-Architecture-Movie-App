package com.example.moviemood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemood.db.MoviesEntity
import com.example.moviemood.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(private val databaserepository:DatabaseRepository):ViewModel() {
    val favoriteMovieList = MutableLiveData<MutableList<MoviesEntity>>()
    val emptyList = MutableLiveData<Boolean>()

    fun loadFavoriteMovieList() = viewModelScope.launch {
        val list = databaserepository.getAllFavoriteList()
        if (list.isNotEmpty()) {
            favoriteMovieList.postValue(list)
            emptyList.postValue(false)
        } else {
            emptyList.postValue(true)
        }
    }
}