package com.example.betteryou.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.betteryou.domain.repository.DatastoreRepository
import javax.inject.Inject

class SetPreferencesUseCase @Inject constructor(
    private val preferencesRepository: DatastoreRepository
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) {
        preferencesRepository.setPreference(key, value)
    }
}
