package com.example.moviemood.utils

sealed class ResultStatu<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultStatu<T>()
    data class Error(val exception: Exception) : ResultStatu<Nothing>()
}