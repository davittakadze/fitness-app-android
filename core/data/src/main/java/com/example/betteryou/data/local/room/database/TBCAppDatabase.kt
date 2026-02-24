package com.example.betteryou.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.betteryou.data.local.room.dao.explore.ExerciseDao
import com.example.betteryou.data.local.room.dao.history.HistoryDao
import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.data.local.room.dao.meal.FavoriteMealDao
import com.example.betteryou.data.local.room.dao.meal.MealDao
import com.example.betteryou.data.local.room.dao.product.ProductDao
import com.example.betteryou.data.local.room.dao.notification.NotificationDao
import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.data.local.room.dao.user_product.UserProductDao
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.explore.ExerciseEntity
import com.example.betteryou.data.local.room.entity.intake.DailyIntakeEntity
import com.example.betteryou.data.local.room.entity.meal.Converters
import com.example.betteryou.data.local.room.entity.meal.FavoriteMealEntity
import com.example.betteryou.data.local.room.entity.meal.MealEntity
import com.example.betteryou.data.local.room.entity.notification.NotificationEntity
import com.example.betteryou.data.local.room.entity.user.UserEntity
import com.example.betteryou.data.local.room.entity.user_products.UserProductEntity
import com.example.betteryou.data.local.room.entity.workout.ExerciseSetEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import com.example.betteryou.data.local.room.entity.product.ProductEntity

@Database(
    entities = [
        UserEntity::class,
        DailyIntakeEntity::class,
        UserProductEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        ExerciseSetEntity::class,
        WorkoutHistoryEntity::class,
        MealEntity::class,
        FavoriteMealEntity::class,
        ExerciseEntity::class,
        ProductEntity::class
        NotificationEntity::class
    ],
    version = 24,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun dailyIntakeDao(): DailyIntakeDao
    abstract fun userProductDao(): UserProductDao
    abstract fun historyDao(): HistoryDao
    abstract fun mealDao(): MealDao
    abstract fun favoriteMealDao(): FavoriteMealDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun productDao(): ProductDao
    abstract fun notificationDao(): NotificationDao
}