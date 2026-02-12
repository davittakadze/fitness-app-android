package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.data.local.room.entity.intake.DailyIntakeEntity
import com.example.betteryou.feature.daily.data.remote.model.IntakeDto
import com.example.betteryou.feature.daily.domain.model.Intake

fun DailyIntakeEntity.toDomain(): Intake=Intake(
    this.consumedCalories,
    this.protein,
    this.fat,
    this.carbs,
    this.water
)