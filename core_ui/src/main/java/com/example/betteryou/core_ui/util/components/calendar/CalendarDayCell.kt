package com.example.betteryou.core_ui.util.components.calendar


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import java.time.LocalDate

@Composable
fun CalendarDayCell(
    date: LocalDate,
    isInCurrentMonth: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background =
        if (isSelected) LocalTBCColors.current.accent
        else LocalTBCColors.current.transparentBack

    val textColor =
        if (isSelected) LocalTBCColors.current.background
        else if (isInCurrentMonth) LocalTBCColors.current.onBackground
        else LocalTBCColors.current.textSecondary

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(background)
            .clickable(enabled = isInCurrentMonth, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = LocalTBCTypography.current.bodyLarge,
            color = textColor
        )
    }
}
