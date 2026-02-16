package com.example.betteryou.data.local.room.dao.intake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.intake.DailyIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyIntakeDao {

    @Query("SELECT * FROM daily_intake WHERE userId = :userId AND date = :date")
    fun getDailyIntake(userId: String, date: Long): Flow<DailyIntakeEntity?>

    @Query(
        """
        UPDATE daily_intake SET 
            consumedCalories = CASE WHEN :calories IS NOT NULL THEN :calories ELSE consumedCalories END,
            protein = CASE WHEN :protein IS NOT NULL THEN :protein ELSE protein END,
            fat = CASE WHEN :fat IS NOT NULL THEN :fat ELSE fat END,
            carbs = CASE WHEN :carbs IS NOT NULL THEN :carbs ELSE carbs END,
            water = CASE WHEN :water IS NOT NULL THEN :water ELSE water END
        WHERE userId = :userId AND date = :date
    """
    )
    suspend fun updateDailyTotals(
        userId: String,
        date: Long,
        calories: Double? = null,
        protein: Double? = null,
        fat: Double? = null,
        carbs: Double? = null,
        water: Double? = null,
    ): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDailyIntake(entity: DailyIntakeEntity)

    suspend fun updateOrInsertDailyIntake(
        userId: String,
        date: Long,
        calories: Double? = null,
        protein: Double? = null,
        fat: Double? = null,
        carbs: Double? = null,
        water: Double? = null,
    ) {
        val updatedRows = updateDailyTotals(
            userId, date, calories, protein, fat, carbs, water
        )
        if (updatedRows == 0) {
            insertDailyIntake(
                DailyIntakeEntity(
                    userId = userId,
                    date = date,
                    consumedCalories = calories ?: 0.0,
                    protein = protein ?: 0.0,
                    fat = fat ?: 0.0,
                    carbs = carbs ?: 0.0,
                    water = water ?: 0.0
                )
            )
        }
    }
}
