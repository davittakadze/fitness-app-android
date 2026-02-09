package com.example.betteryou.feature.menu.domain.repository

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface GoogleSignUpRepository {
    suspend fun signInWithGoogle(idToken: String): Flow<Resource<Unit>>

}