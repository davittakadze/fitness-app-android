package com.betteryou.feature.explore.data.mapper

import com.betteryou.feature.explore.data.model.ExerciseDto
import com.betteryou.feature.explore.domain.model.GetExercise
import com.example.betteryou.data.local.room.entity.explore.ExerciseEntity


fun ExerciseDto.toEntity() = ExerciseEntity(
    id = id,
    titleEn = title.en,
    titleKa = title.ka,
    descriptionEn = description.en,
    descriptionKa = description.ka,
    musclesTargetedEn = musclesTargeted.en,
    musclesTargetedKa = musclesTargeted.ka,
    imageUrl = imageUrl
)
fun ExerciseEntity.toDomain(lang: String) = GetExercise(
    id = id,
    title = if (lang == "ka") titleKa else titleEn,
    description = if (lang == "ka") descriptionKa else descriptionEn,
    musclesTargeted = if (lang == "ka") musclesTargetedKa else musclesTargetedEn,
    imageUrl = imageUrl
)