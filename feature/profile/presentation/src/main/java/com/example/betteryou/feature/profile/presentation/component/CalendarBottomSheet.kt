package com.example.betteryou.feature.profile.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.example.betteryou.core_ui.components.calendar.CalendarContent
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.feature.profile.presentation.ProfileEvent
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnDateSelected
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnMonthPickerToggle
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnNextMonth
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnPrevMonth
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnYearPickerToggle
import com.example.betteryou.feature.profile.presentation.ProfileState
import com.example.betteryou.util.calculateAge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = LocalTBCColors.current.background
    ) {
        CalendarContent(
            month = state.calendarMonth,
            selectedDate = state.selectedDate,
            onPrevMonth = { onEvent(OnPrevMonth) },
            onNextMonth = { onEvent(OnNextMonth) },
            onDateSelected = { onEvent(OnDateSelected(it, calculateAge(it))) },
            onYearClick = {
                onEvent(OnYearPickerToggle)
            },
            onMonthClick = {
                onEvent(OnMonthPickerToggle)
            }
        )
    }
}