package com.betteryou.feature.explore.data.mapper

import com.betteryou.feature.explore.data.model.ExerciseDto
import com.betteryou.feature.explore.domain.model.GetExercise
import com.example.betteryou.data.local.room.entity.explore.ExerciseEntity

fun ExerciseDto.toEntity() = ExerciseEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    description = description,
    musclesTargeted = musclesTargeted
)

fun ExerciseEntity.toDomain() = GetExercise(
    id = id,
    title = title,
    imageUrl = imageUrl,
    description = description,
    musclesTargeted = musclesTargeted
)