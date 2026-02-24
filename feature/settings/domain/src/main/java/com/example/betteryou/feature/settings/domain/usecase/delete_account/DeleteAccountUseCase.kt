package com.example.betteryou.feature.settings.domain.usecase.delete_account

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.settings.domain.repository.delete_account.DeleteAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: DeleteAccountRepository
) {

    suspend operator fun invoke(password:String): Flow<Resource<Unit>> {
        return repository.deleteAccount(password)
    }

}