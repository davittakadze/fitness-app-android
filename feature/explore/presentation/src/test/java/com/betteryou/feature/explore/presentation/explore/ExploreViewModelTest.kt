package com.betteryou.feature.explore.presentation.explore

import app.cash.turbine.test
import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.usecase.GetExercisesUseCase
import com.betteryou.feature.explore.presentation.screen.explore.ExploreEvent
import com.betteryou.feature.explore.presentation.screen.explore.ExploreSideEffect
import com.betteryou.feature.explore.presentation.screen.explore.ExploreViewModel
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
class ExploreViewModelTest : BaseUnitTest() {
    private lateinit var getExercisesUseCase: GetExercisesUseCase
    private lateinit var viewModel: ExploreViewModel

    override fun setUp() {
        super.setUp()
        getExercisesUseCase = mockk()
        viewModel = ExploreViewModel(getExercisesUseCase)
    }

    @Test
    fun `fetchExercises SUCCESS - state should be updated with exercises and loader false`() = runTest {
        val exercises = listOf(
            GetExercise(
                id = "1",
                title = "Push Up",
                imageUrl = "https://example.com/pushup.png",
                description = "Upper body exercise",
                musclesTargeted = listOf("Chest", "Triceps")
            )
        )
        coEvery { getExercisesUseCase.invoke() } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Success(exercises))
        }

        viewModel = ExploreViewModel(getExercisesUseCase)

        viewModel.onEvent(ExploreEvent.FetchExercises)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            Assert.assertFalse(state.isLoading)
            Assert.assertEquals(1, state.exercises.size)
            Assert.assertEquals("Push Up", state.exercises[0].title)
            Assert.assertEquals(1, state.filteredExercises.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchExercises ERROR - state should have loader false`() = runTest {
        coEvery { getExercisesUseCase.invoke() } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Error("Network Error"))
        }

        viewModel = ExploreViewModel(getExercisesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            Assert.assertFalse(state.isLoading)
            Assert.assertTrue(state.exercises.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `onSearchFieldChange - filteredExercises should be filtered by input`() = runTest {
        val exercises = listOf(
            GetExercise(id = "1", title = "Push Up", imageUrl = "", description = "", musclesTargeted = listOf()),
            GetExercise(id = "2", title = "Pull Up", imageUrl = "", description = "", musclesTargeted = listOf()),
            GetExercise(id = "3", title = "Squat", imageUrl = "", description = "", musclesTargeted = listOf())
        )
        coEvery { getExercisesUseCase.invoke() } returns flow {
            emit(Resource.Success(exercises))
        }

        viewModel.onEvent(ExploreEvent.FetchExercises)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.onEvent(ExploreEvent.OnSearchFieldChange("push"))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            Assert.assertEquals(1, state.filteredExercises.size)
            Assert.assertEquals("Push Up", state.filteredExercises[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onExerciseClick - should emit NavigateToDetails side effect`() = runTest {
        coEvery { getExercisesUseCase.invoke() } returns flow {
            emit(Resource.Success(emptyList()))
        }

        viewModel = ExploreViewModel(getExercisesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onEvent(ExploreEvent.OnExerciseClick(workoutId = "1"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is ExploreSideEffect.NavigateToDetails)
            Assert.assertEquals("1", (effect as ExploreSideEffect.NavigateToDetails).workoutId)
            cancelAndIgnoreRemainingEvents()
        }
    }
}