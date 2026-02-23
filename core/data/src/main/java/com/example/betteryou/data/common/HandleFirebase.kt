package com.example.betteryou.data.common

import com.example.betteryou.domain.common.Resource
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class HandleFirebase @Inject constructor() {

    fun <T> safeCall(call: suspend () -> T) = flow<Resource<T>> {
        emit(Resource.Loader(true))
        try {
            val result = call()
            emit(Resource.Success(result))
        } catch (e: Exception) {

            val message = when (e) {

                is IOException ->
                    e.message ?: "no network connection"

                is FirebaseAuthException ->
                    e.message ?: "firebase authentication error"

                else ->
                    e.message ?: "unknown exception"
            }

            emit(Resource.Error(message))
        }
        emit(Resource.Loader(false))
    }
}