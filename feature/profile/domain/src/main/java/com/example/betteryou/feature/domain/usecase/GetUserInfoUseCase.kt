package com.example.betteryou.feature.domain.usecase

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.domain.model.User
import com.example.betteryou.feature.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserProfileRepository,
) {
    operator fun invoke(): Flow<Resource<User?>> {
        return userRepository.getUser()
    }
}
