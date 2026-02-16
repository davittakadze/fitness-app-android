package com.example.betteryou.data.local.room.dao.workout

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.betteryou.data.local.room.entity.workout.ExerciseSetEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    // For Workouts
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

    // For Sets

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: ExerciseSetEntity)

    @Delete
    suspend fun deleteSet(set: ExerciseSetEntity)

    @Query("UPDATE exercise_sets SET weight = :weight, reps = :reps WHERE setId = :setId")
    suspend fun updateSet(setId: Long, weight: String, reps: String)

    @Query("UPDATE exercise_sets SET isCompleted = :isCompleted WHERE setId = :setId")
    suspend fun toggleSetCompletion(setId: Long, isCompleted: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<ExerciseSetEntity>)

    @Transaction
    suspend fun insertFullWorkoutWithSets(
        workout: WorkoutEntity,
        exercises: List<WorkoutExerciseEntity>,
        sets: List<ExerciseSetEntity>
    ) {
        insertWorkout(workout)
        insertExercises(exercises)
        insertSets(sets)
    }

    // For History

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WorkoutHistoryEntity)

}