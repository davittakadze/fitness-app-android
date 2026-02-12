package com.example.betteryou.core_ui.util.components.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

fun buildMonthGrid(
    month: YearMonth,
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
): List<LocalDate> {
    val firstOfMonth = month.atDay(1)

    val offset = (firstOfMonth.dayOfWeek.value - firstDayOfWeek.value + 7) % 7

    val startDate = firstOfMonth.minusDays(offset.toLong())

    return List(42) { i ->
        startDate.plusDays(i.toLong())
    }
}
