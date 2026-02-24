package com.betteryou.feature.history.domain.model

data class GetHistory (
    val id: Long,
    val userId: String,
    val workoutTitle: String,
    val timestamp: Long,
    val durationMillis: Long,
    val exercises: String
)