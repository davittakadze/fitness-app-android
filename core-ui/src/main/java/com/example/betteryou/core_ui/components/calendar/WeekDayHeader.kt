package com.example.betteryou.core_ui.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import java.time.DayOfWeek

@Composable
fun WeekDaysRow(
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    modifier: Modifier = Modifier
) {
    val days = List(7) { i ->
        firstDayOfWeek.plus(i.toLong())
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        days.forEach { dow ->
            Text(
                text = dow.name.take(3),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = LocalTBCTypography.current.bodyMedium,
                color = LocalTBCColors.current.textSecondary
            )
        }
    }
}
