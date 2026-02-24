package com.bettetyou.feature.notification.presentation.component.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bettetyou.feature.notification.domain.model.NotificationType
import com.bettetyou.feature.notification.presentation.model.NotificationUi
import com.bettetyou.feature.notification.presentation.screen.NotificationEvent
import com.bettetyou.feature.notification.presentation.screen.NotificationState
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.SwipeToDeleteContainer
import com.example.betteryou.core_ui.components.TBCAppScreenPlaceholder
import com.example.betteryou.core_ui.theme.Radius.radius16
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme

@Composable
fun HistoryPage(
    state: NotificationState,
    onEvent: (NotificationEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TBCTheme.colors.background)
    ) {
        if (state.notifications.isEmpty()) {
            TBCAppScreenPlaceholder(
                modifier = Modifier.fillMaxSize(),
                primaryText = stringResource(R.string.no_notifications),
                secondaryText = "",
                icon = R.drawable.ic_notification
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.notifications,
                    key = { it.id }
                ) { notification ->
                    SwipeToDeleteContainer(
                        onDelete = {
                            onEvent(NotificationEvent.DeleteNotificationById(notification.id.toInt()))
                        }
                    ) {
                        NotificationItem(notification = notification, onEvent)
                    }
                }
            }
        }

    }
}

@Composable
private fun NotificationItem(notification: NotificationUi, onEvent: (NotificationEvent) -> Unit) {
    Card(
        onClick = {
            when (notification.type) {
                NotificationType.WATER -> {
                    onEvent.invoke(NotificationEvent.OnWaterNotificationClicked(id = notification.id))
                }

                else -> Unit
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = radius16,
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) TBCTheme.colors.card else TBCTheme.colors.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = when (notification.type) {
                NotificationType.WATER -> R.drawable.ic_water_drop
                else -> R.drawable.ic_notification
            }

            val iconColor =
                if (notification.type == NotificationType.WATER) Color.Cyan else Color.Gray

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(Spacer.spacing16))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = TBCTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TBCTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.height(Spacer.spacing8))

                Text(
                    text = notification.body,
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.textSecondary
                )

                Spacer(modifier = Modifier.height(Spacer.spacing4))

                Text(
                    text = notification.date,
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.textSecondary
                )
            }
        }
    }
}