package com.betteryou.feature.history.presentation.screen

import com.betteryou.feature.history.presentation.screen.model.HistoryUI

data class HistoryState (
    val history: List<HistoryUI> = emptyList(),
    val loader: Boolean = false,
)