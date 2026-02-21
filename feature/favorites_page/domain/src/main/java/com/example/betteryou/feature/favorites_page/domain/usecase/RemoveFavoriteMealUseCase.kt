package com.example.betteryou.feature.favorites_page.domain.usecase

import com.example.betteryou.feature.favorites_page.domain.repository.FavoritesPageRepository
import javax.inject.Inject

class RemoveFavoriteMealUseCase @Inject constructor(
    private val repository: FavoritesPageRepository,
) {
    suspend fun invoke(id: Long) {
        repository.removeFavoriteMealById(id)
    }
}