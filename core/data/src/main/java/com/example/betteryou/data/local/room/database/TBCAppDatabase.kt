package com.example.betteryou.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.betteryou.data.local.room.dao.UserDao
import com.example.betteryou.data.local.room.entity.UserEntity
import com.example.betteryou.data.local.room.dao.UserNutritionDao
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.UserNutritionEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity


@Database(
    entities = [
        UserEntity::class,
        UserNutritionEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userNutritionDao(): UserNutritionDao
    abstract fun workoutDao(): WorkoutDao
}