package com.example.betteryou.feature.favorites_page.domain.usecase

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.favorites_page.domain.model.Recipe
import com.example.betteryou.feature.favorites_page.domain.repository.FavoritesPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteMealUseCase @Inject constructor(
    private val repository: FavoritesPageRepository,
) {
    operator fun invoke(userId:String,currentLang:String): Flow<Resource<List<Recipe>>> = repository.getFavoriteMeals(userId,currentLang)
}