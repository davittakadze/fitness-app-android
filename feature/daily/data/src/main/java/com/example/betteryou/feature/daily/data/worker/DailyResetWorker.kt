package com.example.betteryou.feature.daily.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.feature.daily.domain.model.intake.Intake
import com.example.betteryou.feature.daily.domain.usecase.intake.UpdateDailyIntakeUseCase
import com.example.betteryou.feature.daily.domain.usecase.user_daily_product.ClearOldUserDailyProductsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar

@HiltWorker
class DailyResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateDailyIntakeUseCase: UpdateDailyIntakeUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val clearOldUserDailyProductsUseCase: ClearOldUserDailyProductsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val userId = getUserIdUseCase() ?: return Result.failure()
        val todayStart = getStartOfDayMillis()

        val resetIntake = Intake(
            dailyCalories = 0.0,
            protein = 0.0,
            fats = 0.0,
            carbs = 0.0,
            water = 0.0
        )

        updateDailyIntakeUseCase(userId, resetIntake, todayStart)
        clearOldUserDailyProductsUseCase(userId)
        return Result.success()
    }

    private fun getStartOfDayMillis(): Long {
        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)
        return now.timeInMillis
    }

}
