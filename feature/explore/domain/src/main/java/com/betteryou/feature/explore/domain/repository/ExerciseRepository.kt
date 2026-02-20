package com.betteryou.feature.explore.domain.repository

import com.betteryou.feature.explore.domain.model.GetExercise
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExercises(): Flow<Resource<List<GetExercise>>>
    fun getExerciseDetails(id: String): Flow<Resource<GetExercise>>
}