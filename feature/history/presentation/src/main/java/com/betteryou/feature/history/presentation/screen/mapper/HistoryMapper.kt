package com.betteryou.feature.history.presentation.screen.mapper

import com.betteryou.feature.history.domain.model.GetHistory
import com.betteryou.feature.history.presentation.screen.model.ExerciseDetail
import com.betteryou.feature.history.presentation.screen.model.HistoryUI
import kotlinx.serialization.json.Json

fun GetHistory.toPresentation(): HistoryUI {
    val parsedExercises = try {

        val json = Json { ignoreUnknownKeys = true }
        json.decodeFromString<List<ExerciseDetail>>(this.exercises)

    } catch (e: Exception) {
        emptyList()
    }

    return HistoryUI(
        id = this.id,
        workoutTitle = this.workoutTitle,
        timestamp = this.timestamp,
        durationMillis = this.durationMillis,
        exercises = parsedExercises
    )
}