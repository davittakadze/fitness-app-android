package com.example.betteryou.domain

sealed interface Resource<out T> {

    data class Success<out T>(
        val data: T,
    ) : Resource<T>

    data class Error<out T>(
        val errorMessage: String,
        val data: T? = null,
    ) : Resource<T>

    data class Loader<out T>(
        val isLoading: Boolean,
    ) : Resource<T>
}
