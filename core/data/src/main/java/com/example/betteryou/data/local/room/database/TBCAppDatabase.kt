package com.example.betteryou.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.data.local.room.entity.user.UserEntity
import com.example.betteryou.data.local.room.dao.nutrition.NutritionDao
import com.example.betteryou.data.local.room.entity.nutrition.NutritionEntity
import com.example.betteryou.data.local.room.entity.intake.DailyIntakeEntity

@Database(
    entities = [UserEntity::class, NutritionEntity::class, DailyIntakeEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userNutritionDao(): NutritionDao
    abstract fun dailyIntakeDao(): DailyIntakeDao
}