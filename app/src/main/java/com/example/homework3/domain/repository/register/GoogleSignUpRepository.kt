package com.example.homework3.domain.repository.register

import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface GoogleSignUpRepository {
    suspend fun signInWithGoogle(idToken: String): Flow<Resource<Unit>>
    fun signOut()
}
