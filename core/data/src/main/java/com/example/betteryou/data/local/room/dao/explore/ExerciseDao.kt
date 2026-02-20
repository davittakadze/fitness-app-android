package com.example.betteryou.data.local.room.dao.explore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.betteryou.data.local.room.entity.explore.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM explore_exercises")
    suspend fun deleteAllExercises()

    @Query("SELECT * FROM explore_exercises")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Transaction
    suspend fun updateExercises(exercises: List<ExerciseEntity>) {
        deleteAllExercises()
        insertExercises(exercises)
    }

    @Query("SELECT * FROM explore_exercises WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: String): Flow<ExerciseEntity>
}