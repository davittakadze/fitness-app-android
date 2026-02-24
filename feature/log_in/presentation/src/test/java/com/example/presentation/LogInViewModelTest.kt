package com.example.presentation

import app.cash.turbine.test
import com.example.betteryou.domain.common.Resource
import com.example.domain.usecase.login.LogInUseCase
import com.example.domain.usecase.validator.EmailValidatorUseCase
import com.example.domain.usecase.validator.EmptyFieldsValidatorUseCase
import com.example.domain.usecase.validator.PasswordValidatorUseCase
import com.example.presentation.login.LogInEvent
import com.example.presentation.login.LogInSideEffect
import com.example.presentation.login.LogInViewModel
import com.example.testing.BaseUnitTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import kotlin.test.Test


@ExperimentalCoroutinesApi
class LogInViewModelTest : BaseUnitTest() {
    private lateinit var logInUseCase: LogInUseCase
    private val emailValidatorUseCase = EmailValidatorUseCase()
    private val passwordValidatorUseCase = PasswordValidatorUseCase()
    private val emptyFieldsValidatorUseCase = EmptyFieldsValidatorUseCase()
    private lateinit var viewModel: LogInViewModel

    override fun setUp() {
        super.setUp()
        logInUseCase = mockk()
        viewModel = LogInViewModel(
            logInUseCase,
            emailValidatorUseCase,
            passwordValidatorUseCase,
            emptyFieldsValidatorUseCase
        )
    }

    @Test
    fun `onEmailChange - state should be updated with email`() = runTest {
        viewModel.onEvent(LogInEvent.OnEmailChange("test@test.com"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            Assert.assertEquals("test@test.com", state.email)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onPasswordChange - state should be updated with password`() = runTest {
        viewModel.onEvent(LogInEvent.OnPasswordChange("password123"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            Assert.assertEquals("password123", state.password)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogInButtonClick - empty fields should emit ShowError side effect`() = runTest {
        viewModel.onEvent(LogInEvent.OnLogInButtonClick("", ""))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is LogInSideEffect.ShowError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogInButtonClick - invalid email should emit ShowError side effect`() = runTest {
        viewModel.onEvent(LogInEvent.OnLogInButtonClick("invalidemail", "password123"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is LogInSideEffect.ShowError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogInButtonClick - short password should emit ShowError side effect`() = runTest {
        viewModel.onEvent(LogInEvent.OnLogInButtonClick("test@test.com", "123"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is LogInSideEffect.ShowError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogInButtonClick SUCCESS - should emit NavigateHome side effect`() = runTest {
        coEvery { logInUseCase.invoke("test@test.com", "password123") } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Success(Unit))
        }

        viewModel.onEvent(LogInEvent.OnLogInButtonClick("test@test.com", "password123"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is LogInSideEffect.NavigateHome)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLogInButtonClick ERROR - should emit ShowError side effect`() = runTest {
        coEvery { logInUseCase.invoke("test@test.com", "password123") } returns flow {
            emit(Resource.Loader(true))
            emit(Resource.Error("Network Error"))
        }

        viewModel.onEvent(LogInEvent.OnLogInButtonClick("test@test.com", "password123"))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is LogInSideEffect.ShowError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onBackButtonClick - should emit NavigateBack side effect`() = runTest {
        viewModel.onEvent(LogInEvent.OnBackButtonClick)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sideEffect.test {
            val effect = awaitItem()
            Assert.assertTrue(effect is LogInSideEffect.NavigateBack)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `passwordVisibilityChange - state should be updated`() = runTest {
        viewModel.onEvent(LogInEvent.PasswordVisibilityChange(true))
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            Assert.assertTrue(state.isPasswordVisible)
            cancelAndIgnoreRemainingEvents()
        }
    }
}