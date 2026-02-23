package com.example.betteryou.data.di.room

import android.content.Context
import androidx.room.Room
import com.example.betteryou.data.local.room.dao.explore.ExerciseDao
import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.data.local.room.dao.meal.FavoriteMealDao
import com.example.betteryou.data.local.room.dao.meal.MealDao
import com.example.betteryou.data.local.room.dao.nutrition.NutritionDao
import com.example.betteryou.data.local.room.dao.user.UserDao
import com.example.betteryou.data.local.room.dao.user_product.UserProductDao
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao {
        return appDatabase.workoutDao()
    }

    @Provides
    @Singleton
    fun provideUserNutritionDao(db: AppDatabase): NutritionDao = db.userNutritionDao()

    @Provides
    fun provideIntakeDao(db: AppDatabase): DailyIntakeDao = db.dailyIntakeDao()

    @Provides
    fun provideUserDailyProductDao(db: AppDatabase): UserProductDao = db.userProductDao()

    @Provides
    @Singleton
    fun provideHistoryDao(db: AppDatabase) = db.historyDao()

    @Provides
    fun provideMealDao(db: AppDatabase): MealDao = db.mealDao()

    @Provides
    fun provideFavoriteMealDao(db: AppDatabase): FavoriteMealDao = db.favoriteMealDao()

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    fun provideNotificationDao(db: AppDatabase) = db.notificationDao()
}
