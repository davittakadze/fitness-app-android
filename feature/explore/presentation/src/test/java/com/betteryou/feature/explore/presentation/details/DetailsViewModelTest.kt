package com.betteryou.feature.explore.presentation.details

import app.cash.turbine.test
import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.usecase.GetDetailsUseCase
import com.betteryou.feature.explore.presentation.screen.details.DetailsEvent
import com.betteryou.feature.explore.presentation.screen.details.DetailsSideEffect
import com.betteryou.feature.explore.presentation.screen.details.DetailsViewModel
import com.example.betteryou.domain.common.Resource
import com.example.testing.BaseUnitTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import kotlin.test.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest : BaseUnitTest() {
    private lateinit var getDetailsUseCase: GetDetailsUseCase
    private lateinit var viewModel: DetailsViewModel

    override fun setUp() {
        super.setUp()
        getDetailsUseCase = mockk()
        viewModel = DetailsViewModel(getDetailsUseCase)
    }

    @Test
    fun `fetchDetails SUCCESS - state should be updated with exercise and loader false`() = runTest {
        // Given
        val exercise = GetExercise(
            id = "1",
            title = "Push Up",
            imageUrl = "https://example.com/pushup.png",
            description = "Upper body exercise",
            musclesTargeted = listOf("Chest", "Triceps")
        )
        coEvery { getDetailsUseCase.invoke("1") } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Success(exercise))
        }

        // When
        viewModel.onEvent(DetailsEvent.FetchDetails("1"))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            Assert.assertFalse(state.isLoading)
            Assert.assertNotNull(state.exercise)
            Assert.assertEquals("Push Up", state.exercise?.title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchDetails ERROR - state should have loader false and error side effect emitted`() = runTest {
        // Given
        coEvery { getDetailsUseCase.invoke("1") } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Error("Network Error"))
        }

        // When
        viewModel.onEvent(DetailsEvent.FetchDetails("1"))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            Assert.assertFalse(state.isLoading)
            Assert.assertNull(state.exercise)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `navigateBack - should emit NavigateBack side effect`() = runTest {
        // When
        viewModel.onEvent(DetailsEvent.NavigateBack)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is DetailsSideEffect.NavigateBack)
            cancelAndIgnoreRemainingEvents()
        }
    }
}