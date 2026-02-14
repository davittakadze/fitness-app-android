package com.betteryou.workout.data.repository

import com.betteryou.workout.data.remote.api_service.GetWorkoutsApiService
import com.betteryou.workout.data.remote.mapper.toDomain
import com.betteryou.workout.data.remote.model.ExerciseDto
import com.betteryou.workout.domain.repository.WorkoutsRepository
import com.bettetyou.core.model.GetExercise
import com.bettetyou.core.model.Workout
import com.example.betteryou.data.common.HandleResponse
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.workout.WorkoutEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity
import com.example.betteryou.data.mapper.asResource
import com.example.betteryou.data.mapper.toDomain
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class WorkoutsRepositoryImpl @Inject constructor(
    private val apiService: GetWorkoutsApiService,
    private val workoutDao: WorkoutDao,
    private val handleResponse: HandleResponse
) : WorkoutsRepository {

    override suspend fun getWorkoutsFromApi(): Flow<Resource<List<GetExercise>>> {
        return handleResponse.safeApiCall {
            apiService.getWorkouts()
        }.asResource { it.map(ExerciseDto::toDomain) }
    }


    override suspend fun saveWorkout(
        title: String,
        exercises: List<GetExercise>,
    ): String {
        // 1. ვაგენერირებთ უნიკალურ ID-ს ვორქაუთისთვის
        val workoutId = UUID.randomUUID().toString()
        val currentTime = System.currentTimeMillis()

        // 2. ვქმნით მთავარ ვორქაუთის ენთითის
        val workoutEntity = WorkoutEntity(
            id = workoutId,
            title = title,
            createdAt = currentTime
        )

        // 3. GetExercise-ების სიას ვაქცევთ ბაზის ენთითებად
        // თითოეულ სავარჯიშოს "ვაბამთ" ამ ვორქაუთის ID-ზე
        val exerciseEntities = exercises.map { exercise ->
            WorkoutExerciseEntity(
                workoutId = workoutId, // Foreign Key
                exerciseId = exercise.id,
                name = exercise.name,
                category = exercise.category,
                imageUrl = exercise.imageUrl
            )
        }

        // 4. ვინახავთ ბაზაში (სასურველია DAO-ში გქონდეს @Transaction ფუნქცია ორივესთვის)
        workoutDao.insertFullWorkout(workoutEntity, exerciseEntities)

        return workoutId
    }

    override suspend fun deleteWorkout(workoutId: String) {
        workoutDao.deleteWorkoutById(workoutId)
    }

    override fun getWorkoutById(id: String): Flow<Workout?> {
        return workoutDao.getWorkoutById(id).map { roomModel ->
            roomModel?.toDomain()
        }
    }

    override fun getAllSavedWorkouts(): Flow<List<Workout>> {
        return workoutDao.getAllWorkouts().map { list ->
            list.map { it.toDomain() }
        }
    }
}