package com.betteryou.feature.register.domain.repository

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(email:String,password:String): Flow<Resource<Unit>>
}