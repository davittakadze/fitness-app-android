package com.example.betteryou.domain.repository.register

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface GoogleSignUpRepository {
    suspend fun signInWithGoogle(idToken: String): Flow<Resource<Unit>>
    fun signOut()
}
