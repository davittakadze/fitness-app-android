package com.example.betteryou.domain.repository.register

import com.example.betteryou.domain.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(email:String,password:String): Flow<Resource<Unit>>
}
