package com.example.betteryou.data.mapper

import com.bettetyou.core.model.ExerciseSet
import com.bettetyou.core.model.GetExercise
import com.bettetyou.core.model.Workout
import com.bettetyou.core.model.WorkoutExercise
import com.example.betteryou.data.local.room.dao.workout.WorkoutWithExercises

fun WorkoutWithExercises.toDomain(): Workout {
    return Workout(
        id = this.workout.id,
        title = this.workout.title,
        exercises = this.exercises.map { exerciseWithSets ->
            WorkoutExercise(
                id = exerciseWithSets.exercise.id,
                exercise = GetExercise(
                    id = exerciseWithSets.exercise.exerciseId,
                    name = exerciseWithSets.exercise.name,
                    category = exerciseWithSets.exercise.category,
                    imageUrl = exerciseWithSets.exercise.imageUrl
                ),
                sets = exerciseWithSets.sets.map { setEntity ->
                    ExerciseSet(
                        id = setEntity.setId,
                        reps = setEntity.reps,
                        weight = setEntity.weight,
                        isCompleted = setEntity.isCompleted
                    )
                }
            )
        }
    )
}