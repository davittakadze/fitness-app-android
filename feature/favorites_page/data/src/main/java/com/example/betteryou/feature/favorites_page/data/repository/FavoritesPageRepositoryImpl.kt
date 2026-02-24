package com.example.betteryou.feature.favorites_page.data.repository

import com.example.betteryou.data.local.room.dao.meal.FavoriteMealDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.favorites_page.data.mapper.toDomain
import com.example.betteryou.feature.favorites_page.domain.model.Recipe
import com.example.betteryou.feature.favorites_page.domain.repository.FavoritesPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoritesPageRepositoryImpl @Inject constructor(
    private val favoriteMealDao: FavoriteMealDao,
) : FavoritesPageRepository {
    override fun getFavoriteMeals(userId: String, currentLang: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loader(true))
        try {
            val favorites = favoriteMealDao.getMealsForUser(userId)
            emit(Resource.Success(favorites.map { it.toDomain(currentLang) }))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun removeFavoriteMealById(mealId: Long, userId: String) {
        favoriteMealDao.deleteMealByIdForUser(mealId,userId)
    }
}