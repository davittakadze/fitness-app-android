package com.example.betteryou.navigation

data class BottomTab<T : Any>(
    val route: T,
    val iconRes: Int
)
