package com.example.betteryou.data.di.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.betteryou.data.local.room.dao.UserDao
import com.example.betteryou.data.local.room.dao.UserNutritionDao
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.data.local.room.dao.nutrition.NutritionDao
import com.example.betteryou.data.local.room.dao.user_product.UserProductDao
import com.example.betteryou.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "better_you_db"
        ).fallbackToDestructiveMigration()
         .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideUserNutritionDao(db: AppDatabase): UserNutritionDao = db.userNutritionDao()

    @Provides
    @Singleton
    fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao {
        return appDatabase.workoutDao()
    }
    fun provideUserNutritionDao(db: AppDatabase): NutritionDao = db.userNutritionDao()

    @Provides
    fun provideIntakeDao(db: AppDatabase): DailyIntakeDao = db.dailyIntakeDao()

    @Provides
    fun provideUserDailyProductDao(db: AppDatabase): UserProductDao = db.userProductDao()
}
