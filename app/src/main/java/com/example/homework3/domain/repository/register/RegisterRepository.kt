package com.example.homework3.domain.repository.register

import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(email:String,password:String): Flow<Resource<Unit>>
}
