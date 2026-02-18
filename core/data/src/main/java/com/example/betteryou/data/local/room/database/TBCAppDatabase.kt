package com.example.betteryou.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.data.local.room.dao.meal.FavoriteMealDao
import com.example.betteryou.data.local.room.dao.meal.MealDao
import com.example.betteryou.data.local.room.dao.nutrition.NutritionDao
import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.data.local.room.dao.user_product.UserProductDao
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.intake.DailyIntakeEntity
import com.example.betteryou.data.local.room.entity.meal.Converters
import com.example.betteryou.data.local.room.entity.meal.FavoriteMealEntity
import com.example.betteryou.data.local.room.entity.nutrition.NutritionEntity
import com.example.betteryou.data.local.room.entity.user.UserEntity
import com.example.betteryou.data.local.room.entity.user_products.UserProductEntity
import com.example.betteryou.data.local.room.entity.workout.ExerciseSetEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import com.example.betteryou.data.local.room.entity.meal.MealEntity

@Database(
    entities = [
        UserEntity::class,
        NutritionEntity::class,
        DailyIntakeEntity::class,
        UserProductEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        ExerciseSetEntity::class,
        WorkoutHistoryEntity::class,
        MealEntity::class,
        FavoriteMealEntity::class
    ],
    version = 14,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun userNutritionDao(): NutritionDao
    abstract fun dailyIntakeDao(): DailyIntakeDao
    abstract fun userProductDao(): UserProductDao
    abstract fun mealDao(): MealDao
    abstract fun favoriteMealDao(): FavoriteMealDao
}