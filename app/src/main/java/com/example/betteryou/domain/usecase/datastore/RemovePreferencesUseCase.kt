package com.example.betteryou.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.betteryou.domain.repository.datastore.DatastoreRepository
import javax.inject.Inject

class RemovePreferencesUseCase @Inject constructor(
    private val preferencesRepository: DatastoreRepository
)  {
    suspend operator fun invoke(keys: List<Preferences.Key<*>>) {
        preferencesRepository.removePreferences(keys)
    }
}
