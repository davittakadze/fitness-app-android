package com.betteryou.feature.explore.domain

import app.cash.turbine.test
import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import com.betteryou.feature.explore.domain.usecase.GetExercisesUseCase
import com.example.betteryou.domain.common.Resource
import com.example.testing.BaseUnitTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class GetExercisesUseCaseTest : BaseUnitTest() {
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var useCase: GetExercisesUseCase

    override fun setUp() {
        super.setUp()
        exerciseRepository = mockk()
        useCase = GetExercisesUseCase(exerciseRepository)
    }

    @Test
    fun `invoke - should return exercises from repository`() = runTest {
        // Given
        val exercises = listOf(
            GetExercise(
                id = "1",
                title = "Push Up",
                imageUrl = "https://example.com/pushup.png",
                description = "Upper body exercise",
                musclesTargeted = listOf("Chest", "Triceps")
            )
        )
        coEvery { exerciseRepository.getExercises() } returns flow {
            emit(Resource.Success(exercises))
        }

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            val item = awaitItem()
            Assert.assertTrue(item is Resource.Success)
            Assert.assertEquals(1, (item as Resource.Success).data.size)
            Assert.assertEquals("Push Up", item.data[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke - should return error from repository`() = runTest {
        // Given
        coEvery { exerciseRepository.getExercises() } returns flow {
            emit(Resource.Error("Network Error"))
        }

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            val item = awaitItem()
            Assert.assertTrue(item is Resource.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}