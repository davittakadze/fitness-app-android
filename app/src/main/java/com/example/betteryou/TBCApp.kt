package com.example.betteryou

import android.app.Application
import com.example.betteryou.feature.daily.data.worker.DailyResetScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TBCApp:Application (){
    @Inject
    lateinit var dailyResetScheduler: DailyResetScheduler

    override fun onCreate() {
        super.onCreate()
        dailyResetScheduler.schedule()
    }
}
