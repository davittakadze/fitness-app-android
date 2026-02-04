package com.example.betteryou.presentation

fun reduceCalendarState(
    state: CalendarUiState,
    action: CalendarAction
): CalendarUiState =
    when (action) {
        CalendarAction.PreviousMonth ->
            state.copy(currentMonth = state.currentMonth.minusMonths(1))
        CalendarAction.NextMonth ->
            state.copy(currentMonth = state.currentMonth.plusMonths(1))
    }
