package com.betteryou.workout.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto (
    val id: String,
    val name: String,
    val category: String,
    @SerialName("image_url")
    val imageUrl: String,
)