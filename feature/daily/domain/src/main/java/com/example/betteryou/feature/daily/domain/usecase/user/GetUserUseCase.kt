package com.example.betteryou.feature.daily.domain.usecase.user

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.user.User
import com.example.betteryou.feature.daily.domain.repository.user.GetUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: GetUserRepository,
) {
    operator fun invoke(): Flow<Resource<User>> {
        return repository.getUser()
    }
}