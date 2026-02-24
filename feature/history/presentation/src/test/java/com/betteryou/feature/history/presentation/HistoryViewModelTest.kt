package com.betteryou.feature.history.presentation

import app.cash.turbine.test
import com.betteryou.feature.history.domain.model.GetHistory
import com.betteryou.feature.history.domain.usecase.DeleteHistoryUseCase
import com.betteryou.feature.history.domain.usecase.GetAllHistoryUseCase
import com.betteryou.feature.history.presentation.screen.HistoryEvent
import com.betteryou.feature.history.presentation.screen.HistorySideEffect
import com.betteryou.feature.history.presentation.screen.HistoryViewModel
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.testing.BaseUnitTest
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import kotlin.test.Test

@ExperimentalCoroutinesApi
class HistoryViewModelTest : BaseUnitTest() {
    private lateinit var getAllHistoryUseCase: GetAllHistoryUseCase
    private lateinit var deleteHistoryUseCase: DeleteHistoryUseCase
    private lateinit var getUserIdUseCase: GetUserIdUseCase
    private lateinit var viewModel: HistoryViewModel

    override fun setUp() {
        super.setUp()
        getAllHistoryUseCase = mockk()
        deleteHistoryUseCase = mockk()
        getUserIdUseCase = mockk()
        viewModel = HistoryViewModel(getAllHistoryUseCase, deleteHistoryUseCase, getUserIdUseCase)
    }

    @Test
    fun `loadHistory SUCCESS - state should be updated with history and loader false`() = runTest {
        // Given
        val history = listOf(
            GetHistory(
                id = 1L,
                userId = "user123",
                workoutTitle = "Morning Workout",
                timestamp = 1706601600000,
                durationMillis = 3600000,
                exercises = "Push Up, Pull Up"
            )
        )
        every { getUserIdUseCase.invoke() } returns "user123"
        every { getAllHistoryUseCase.invoke("user123") } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Success(history))
        }

        // When
        viewModel.onEvent(HistoryEvent.LoadHistory)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            Assert.assertFalse(state.loader)
            Assert.assertEquals(1, state.history.size)
            Assert.assertEquals("Morning Workout", state.history[0].workoutTitle)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadHistory ERROR - state should have loader false`() = runTest {
        // Given
        every { getUserIdUseCase.invoke() } returns "user123"
        every { getAllHistoryUseCase.invoke("user123") } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Error("Network Error"))
        }

        // When
        viewModel.onEvent(HistoryEvent.LoadHistory)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            Assert.assertFalse(state.loader)
            Assert.assertTrue(state.history.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadHistory - when userId is null should use empty string`() = runTest {
        // Given
        every { getUserIdUseCase.invoke() } returns null
        every { getAllHistoryUseCase.invoke("") } returns flow {
            emit(Resource.Success(emptyList()))
        }

        // When
        viewModel.onEvent(HistoryEvent.LoadHistory)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            Assert.assertTrue(state.history.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deleteHistory - should call deleteHistoryUseCase with correct id`() = runTest {
        // Given
        coEvery { deleteHistoryUseCase.invoke(1L) } just Runs

        // When
        viewModel.onEvent(HistoryEvent.DeleteHistory(1L))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { deleteHistoryUseCase.invoke(1L) }
    }

    @Test
    fun `onBackClick - should emit NavigateBack side effect`() = runTest {
        // When
        viewModel.onEvent(HistoryEvent.OnBackClick)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is HistorySideEffect.NavigateBack)
            cancelAndIgnoreRemainingEvents()
        }
    }
}