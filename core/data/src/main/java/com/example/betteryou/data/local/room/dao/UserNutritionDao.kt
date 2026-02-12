package com.example.betteryou.data.local.room.dao

import androidx.room.*
import com.example.betteryou.data.local.room.entity.UserNutritionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserNutritionDao {

    @Query("SELECT * FROM user_nutrition WHERE userId = :userId LIMIT 1")
    fun getUserNutritionById(userId: String): Flow<UserNutritionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserNutrition(user: UserNutritionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserNutritions(users: List<UserNutritionEntity>)

    @Query("DELETE FROM user_nutrition WHERE userId = :userId")
    suspend fun deleteUserNutritionById(userId: String)
}
