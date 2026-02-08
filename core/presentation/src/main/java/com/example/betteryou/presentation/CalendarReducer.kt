package com.example.betteryou.presentation

import java.time.YearMonth

fun reduceMonth(
    currentMonth: YearMonth,
    action: CalendarAction
): YearMonth =
    when (action) {
        CalendarAction.PreviousMonth -> currentMonth.minusMonths(1)
        CalendarAction.NextMonth -> currentMonth.plusMonths(1)
    }
