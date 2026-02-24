package com.example.betteryou.feature.settings.domain.repository.delete_account

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface DeleteAccountRepository {

    suspend fun deleteAccount(password: String): Flow<Resource<Unit>>

}