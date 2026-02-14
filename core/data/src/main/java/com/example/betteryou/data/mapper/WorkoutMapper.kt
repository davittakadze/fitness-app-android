package com.example.betteryou.data.mapper

import com.bettetyou.core.model.Workout
import com.example.betteryou.data.local.room.dao.workout.WorkoutWithExercises

fun WorkoutWithExercises.toDomain(): Workout {
    return Workout(
        id = this.workout.id,
        title = this.workout.title,
        exercises = this.exercises.map { it.toDomain() }
    )
}

