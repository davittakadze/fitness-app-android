package com.example.betteryou.data.local.room.dao.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM workout_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<WorkoutHistoryEntity>>

    // კონკრეტული ისტორიის ჩანაწერის წამოღება ID-ით (დეტალებისთვის)
    @Query("SELECT * FROM workout_history WHERE id = :historyId")
    suspend fun getHistoryById(historyId: Long): WorkoutHistoryEntity?

    // ისტორიაში ჩაწერა (ვარჯიშის დასრულებისას)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WorkoutHistoryEntity)

    // ისტორიიდან ჩანაწერის წაშლა (ჩვენი სვაიპისთვის)
    @Query("DELETE FROM workout_history WHERE id = :historyId")
    suspend fun deleteHistoryById(historyId: Long)

    // ისტორიის სრული გასუფთავება (თუ დაგვჭირდა)
    @Query("DELETE FROM workout_history")
    suspend fun clearAllHistory()
}