package com.betteryou.feature.explore.domain

import app.cash.turbine.test
import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import com.betteryou.feature.explore.domain.usecase.GetDetailsUseCase
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
class GetDetailsUseCaseTest : BaseUnitTest() {
    private lateinit var exerciseRepository: ExerciseRepository
    private lateinit var useCase: GetDetailsUseCase

    override fun setUp() {
        super.setUp()
        exerciseRepository = mockk()
        useCase = GetDetailsUseCase(exerciseRepository)
    }

    @Test
    fun `invoke - should return exercise details from repository`() = runTest {
        // Given
        val exercise = GetExercise(
            id = "1",
            title = "Push Up",
            imageUrl = "https://example.com/pushup.png",
            description = "Upper body exercise",
            musclesTargeted = listOf("Chest", "Triceps")
        )
        coEvery { exerciseRepository.getExerciseDetails("1") } returns flow {
            emit(Resource.Success(exercise))
        }

        // When
        val result = useCase.invoke("1")

        // Then
        result.test {
            val item = awaitItem()
            Assert.assertTrue(item is Resource.Success)
            Assert.assertEquals("Push Up", (item as Resource.Success).data.title)
            Assert.assertEquals("1", item.data.id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke - should return error from repository`() = runTest {
        // Given
        coEvery { exerciseRepository.getExerciseDetails("1") } returns flow {
            emit(Resource.Error("Network Error"))
        }

        // When
        val result = useCase.invoke("1")

        // Then
        result.test {
            val item = awaitItem()
            Assert.assertTrue(item is Resource.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}