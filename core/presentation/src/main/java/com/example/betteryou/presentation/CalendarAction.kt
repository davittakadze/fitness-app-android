package com.example.betteryou.presentation

sealed interface CalendarAction {
    data object PreviousMonth : CalendarAction
    data object NextMonth : CalendarAction
}
