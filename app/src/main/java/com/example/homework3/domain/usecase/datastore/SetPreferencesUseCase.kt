package com.example.homework3.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.homework3.domain.repository.datastore.DatastoreRepository
import javax.inject.Inject

class SetPreferencesUseCase @Inject constructor(
    private val preferencesRepository: DatastoreRepository
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) {
        preferencesRepository.setPreference(key, value)
    }
}
