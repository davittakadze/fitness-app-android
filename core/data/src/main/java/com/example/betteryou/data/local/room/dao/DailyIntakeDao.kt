package com.example.betteryou.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.DailyIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyIntakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyIntake(entity: DailyIntakeEntity)

    @Query("SELECT * FROM daily_intake WHERE userId = :userId AND date = :date")
    fun getDailyIntake(userId: String, date: Long): Flow<DailyIntakeEntity?>

    @Query(
        """
    UPDATE daily_intake SET 
        consumedCalories = consumedCalories + :calories,
        protein = protein + :protein,
        fat = fat + :fat,
        carbs = carbs + :carbs,
        water = water + :water
    WHERE userId = :userId AND date = :date
"""
    )
    suspend fun updateDailyTotals(
        userId: String,
        date: Long,
        calories: Int,
        protein: Int,
        fat: Int,
        carbs: Int,
        water: Double,
    )

}
