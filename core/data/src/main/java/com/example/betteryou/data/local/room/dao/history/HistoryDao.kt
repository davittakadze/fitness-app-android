package com.example.betteryou.data.local.room.dao.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM workout_history WHERE userId = :userId ORDER BY timestamp DESC")
    fun getHistoryByUserId(userId: String): Flow<List<WorkoutHistoryEntity>>

    @Query("SELECT * FROM workout_history WHERE id = :historyId")
    suspend fun getHistoryById(historyId: Long): WorkoutHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WorkoutHistoryEntity)

    @Query("DELETE FROM workout_history WHERE id = :historyId")
    suspend fun deleteHistoryById(historyId: Long)

    @Query("DELETE FROM workout_history")
    suspend fun clearAllHistory()
}