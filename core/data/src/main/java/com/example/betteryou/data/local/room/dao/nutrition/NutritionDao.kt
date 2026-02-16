package com.example.betteryou.data.local.room.dao.nutrition

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.nutrition.NutritionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionDao {

    @Query("SELECT * FROM user_nutrition WHERE userId = :userId LIMIT 1")
    fun getUserNutritionById(userId: String): Flow<NutritionEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUserNutrition(user: NutritionEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertUserNutritions(users: List<NutritionEntity>)

    @Query("DELETE FROM user_nutrition WHERE userId = :userId")
    suspend fun deleteUserNutritionById(userId: String)
}