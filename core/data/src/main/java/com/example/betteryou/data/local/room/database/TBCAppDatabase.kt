package com.example.betteryou.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.betteryou.data.local.room.dao.UserDao
import com.example.betteryou.data.local.room.entity.UserEntity
import com.example.betteryou.data.local.room.dao.UserNutritionDao
import com.example.betteryou.data.local.room.entity.UserNutritionEntity


@Database(
    entities = [UserEntity::class, UserNutritionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userNutritionDao(): UserNutritionDao
}