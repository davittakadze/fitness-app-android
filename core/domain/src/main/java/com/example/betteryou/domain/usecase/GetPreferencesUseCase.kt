package com.example.betteryou.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.betteryou.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferencesUseCase @Inject constructor(private val preferencesRepository: DatastoreRepository)  {
    operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}