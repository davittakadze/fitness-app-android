package com.bettetyou.feature.notification.presentation.component.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bettetyou.feature.notification.presentation.component.ReminderItem
import com.bettetyou.feature.notification.presentation.screen.NotificationEvent
import com.bettetyou.feature.notification.presentation.screen.NotificationState
import com.example.betteryou.core_ui.R

@Composable
fun RemindersPage(
    state: NotificationState,
    onEvent: (NotificationEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ReminderItem(
                title = "Water Intake",
                description = "Get notified to stay hydrated throughout the day",
                icon = R.drawable.ic_water_drop,
                isChecked = state.isWaterReminderEnabled,
                onCheckedChange = { isChecked ->
                    onEvent(NotificationEvent.OnWaterReminderToggled(isChecked))
                }
            )
        }
    }
}