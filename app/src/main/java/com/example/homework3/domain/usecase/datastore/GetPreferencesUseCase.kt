package com.example.homework3.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.homework3.domain.repository.datastore.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferencesUseCase @Inject constructor(private val preferencesRepository: DatastoreRepository)  {
    operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}
