package com.example.betteryou.core_ui.util.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarContent(
    month: YearMonth,
    selectedDate: LocalDate?,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onYearClick: () -> Unit,
    onMonthClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalTBCColors.current.background)
            .padding(vertical = 32.dp, horizontal = 16.dp)
    ) {
        CalendarHeader(
            month = month,
            onPrevMonth = onPrevMonth,
            onNextMonth = onNextMonth,
            onYearClick = onYearClick,
            onMonthClick = onMonthClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        WeekDaysRow()

        Spacer(modifier = Modifier.height(8.dp))

        CalendarGrid(
            month = month,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )
    }
}

