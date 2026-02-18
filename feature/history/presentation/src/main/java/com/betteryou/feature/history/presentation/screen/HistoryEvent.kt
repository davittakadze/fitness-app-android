package com.betteryou.feature.history.presentation.screen

sealed class HistoryEvent {
    object LoadHistory : HistoryEvent()
    data class DeleteHistory(val id: Long) : HistoryEvent()
    data object OnBackClick : HistoryEvent()
}