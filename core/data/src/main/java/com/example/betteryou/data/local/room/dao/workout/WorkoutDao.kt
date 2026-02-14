package com.example.betteryou.data.local.room.dao.workout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.betteryou.data.local.room.entity.workout.WorkoutEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Transaction
    suspend fun insertFullWorkout(workout: WorkoutEntity, exercises: List<WorkoutExerciseEntity>) {
        insertWorkout(workout)
        insertExercises(exercises)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<WorkoutExerciseEntity>)

    @Transaction
    @Query("SELECT * FROM workouts ORDER BY createdAt DESC")
    fun getAllWorkouts(): Flow<List<WorkoutWithExercises>>

    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    fun getWorkoutById(workoutId: String): Flow<WorkoutWithExercises?>

    @Query("DELETE FROM workouts WHERE id = :workoutId")
    suspend fun deleteWorkoutById(workoutId: String)
}