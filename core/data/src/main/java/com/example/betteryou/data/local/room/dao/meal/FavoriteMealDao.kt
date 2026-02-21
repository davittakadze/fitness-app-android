package com.example.betteryou.data.local.room.dao.meal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.betteryou.data.local.room.entity.meal.FavoriteMealEntity

@Dao
interface FavoriteMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: FavoriteMealEntity)

    @Query("SELECT * FROM favorite_meals")
    fun getAllMeals(): List<FavoriteMealEntity>

    @Query("DELETE FROM favorite_meals WHERE id = :mealId")
    suspend fun deleteMealById(mealId: Long)
}