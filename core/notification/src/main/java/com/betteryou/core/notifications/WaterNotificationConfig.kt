package com.betteryou.core.notifications

import com.example.betteryou.core_ui.R
import com.example.betteryou.domain.common.NotificationConfig
import javax.inject.Inject

class WaterNotificationConfig @Inject constructor() : NotificationConfig {
    override val channelId = "water_reminders"
    override val channelName = "Water Intake"
    override val smallIconResId = R.drawable.ic_water_drop
    override val deepLinkUri: String = "tbcapp://daily"
}