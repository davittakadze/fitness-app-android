package com.example.betteryou.domain.usecase

import com.example.betteryou.domain.repository.GetUserIdRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val repository: GetUserIdRepository
) {
    operator fun invoke(): String? {
        return repository.getUserId()
    }
}