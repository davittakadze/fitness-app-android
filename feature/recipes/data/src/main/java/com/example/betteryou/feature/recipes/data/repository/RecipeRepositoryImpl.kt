package com.example.betteryou.feature.recipes.data.repository

import com.example.betteryou.data.common.HandleResponse
import com.example.betteryou.data.local.room.dao.meal.MealDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.recipes.data.remote.service.RecipeService
import com.example.betteryou.feature.recipes.data.remote.mapper.toDomain
import com.example.betteryou.feature.recipes.data.remote.mapper.toEntity
import com.example.betteryou.feature.recipes.domain.model.Recipe
import com.example.betteryou.feature.recipes.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOn

class RecipeRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val recipeService: RecipeService,
    private val mealDao: MealDao,
) : RecipeRepository {
    override suspend fun getMeals(): Flow<Resource<List<Recipe>>> {
        return handleResponse.safeApiCall {
            recipeService.getMeals()
        }.flowOn(Dispatchers.IO)
         .map { resource ->
            when (resource) {
                is Resource.Success -> {
                    val dtoList = resource.data
                    val entities = dtoList.map { it.toEntity() }
                    mealDao.insertMeals(entities)
                    Resource.Success(entities.map { it.toDomain() })
                }

                is Resource.Error -> {
                    val cached = mealDao.getAllMeals()
                    if (cached.isNotEmpty()) {
                        Resource.Success(cached.map { it.toDomain() })
                    } else {
                        Resource.Error(resource.errorMessage)
                    }
                }

                is Resource.Loader -> {
                    Resource.Loader(resource.isLoading)
                }
            }
        }
    }
}