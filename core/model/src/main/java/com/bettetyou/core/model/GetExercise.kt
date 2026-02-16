package com.bettetyou.core.model

import kotlinx.serialization.Serializable

@Serializable
data class GetExercise (
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String,
)