package com.example.betteryou.feature.domain.repository

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun uploadUserProfile(user: User): Flow<Resource<Unit>>
    fun getUser(): Flow<Resource<User?>>
}
