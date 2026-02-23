package com.bettetyou.feature.notification.domain.repository

interface WaterReminderScheduler {
    fun schedule()
    fun cancel()
}