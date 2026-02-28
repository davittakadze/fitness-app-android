package com.betteryou.feature.explore.data.repository

import com.betteryou.feature.explore.data.api_service.GetExercisesApiService
import com.betteryou.feature.explore.data.mapper.toDomain
import com.betteryou.feature.explore.data.mapper.toEntity
import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import com.example.betteryou.data.common.HandleResponse
import com.example.betteryou.data.local.room.dao.explore.ExerciseDao
import com.example.betteryou.data.local.room.entity.explore.ExerciseEntity
import com.example.betteryou.domain.common.DatastoreKeys
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.repository.DatastoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val apiService: GetExercisesApiService,
    private val handleResponse: HandleResponse,
    private val datastoreRepository: DatastoreRepository
) : ExerciseRepository {

    override suspend fun getExercises(): Flow<Resource<List<GetExercise>>> {
        return handleResponse.safeApiCall { apiService.getExercises() }
            .onEach { resource ->
                if (resource is Resource.Success) {
                    exerciseDao.updateExercises(resource.data.map { it.toEntity() })
                }
            }
            .flatMapLatest { apiResource ->
                combine(
                    exerciseDao.getAllExercises(),
                    datastoreRepository.getPreference(DatastoreKeys.USER_LANGUAGE_KEY, "en")
                ) { entities, lang ->
                    val domainList = entities.map { it.toDomain(lang) }

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
        return combine(
            exerciseDao.getExerciseById(id),
            datastoreRepository.getPreference(DatastoreKeys.USER_LANGUAGE_KEY, "en")
        ) { entity, lang ->
            Resource.Success(entity.toDomain(lang)) as Resource<GetExercise>
        }.onStart {
            emit(Resource.Loader(isLoading = true))
        }
    }
}