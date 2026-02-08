package com.example.betteryou.core_ui.util.components.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.YearMonth
import java.time.LocalDate
import java.time.DayOfWeek
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalendarGrid(
    month: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    modifier: Modifier = Modifier
){
    val days = remember(month, firstDayOfWeek) {
        buildMonthGrid(month, firstDayOfWeek)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier.fillMaxWidth(),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days) { date ->
            val isInCurrentMonth = date.month == month.month && date.year == month.year
            val isSelected = selectedDate == date

            CalendarDayCell(
                date = date,
                isInCurrentMonth = isInCurrentMonth,
                isSelected = isSelected,
                onClick = { onDateSelected(date) }
            )
        }
    }
}
