package com.example.betteryou.feature.profile.domain.usecase.usecase

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.profile.domain.model.User
import com.example.betteryou.feature.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadUserInfoUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(user: User): Flow<Resource<Unit>> {

        return repository.uploadUserProfile(user)

    }
}