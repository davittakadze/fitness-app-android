package com.betteryou.feature.explore.data.repository

import com.betteryou.feature.explore.data.api_service.GetExercisesApiService
import com.betteryou.feature.explore.data.mapper.toDomain
import com.betteryou.feature.explore.data.mapper.toEntity
import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import com.example.betteryou.data.common.HandleResponse
import com.example.betteryou.data.local.room.dao.explore.ExerciseDao
import com.example.betteryou.data.local.room.entity.explore.ExerciseEntity
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val apiService: GetExercisesApiService,
    private val handleResponse: HandleResponse
) : ExerciseRepository {
    override suspend fun getExercises(): Flow<Resource<List<GetExercise>>> {
        return handleResponse.safeApiCall { apiService.getExercises() }
            .onEach { resource ->
                if (resource is Resource.Success) {
                    exerciseDao.updateExercises(resource.data.map { it.toEntity() })
                }
            }
            .flatMapLatest { apiResource ->
                exerciseDao.getAllExercises().map { entities ->
                    val domainList = entities.map { it.toDomain() }

                    if (apiResource is Resource.Error && domainList.isEmpty()) {
                        Resource.Error(apiResource.errorMessage)
                    } else {
                        Resource.Success(domainList)
                    }
                }
            }.onStart {
            emit(Resource.Loader(isLoading = true))
        }
    }

    override fun getExerciseDetails(id: String): Flow<Resource<GetExercise>> {
        return exerciseDao.getExerciseById(id).map<ExerciseEntity, Resource<GetExercise>> { entity ->
            Resource.Success(entity.toDomain())
        }.onStart {
            emit(Resource.Loader(isLoading = true))
        }
    }
}