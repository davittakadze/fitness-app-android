package com.example.betteryou.presentation.screen.splash

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.usecase.auth.AuthUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) :
    BaseViewModel<Unit, SplashEvent, SplashSideEffect>(Unit) {
    private var job: Job? = null
    override fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnStartSplash -> startSplash()
            SplashEvent.OnStopSplash -> stopSplash()
        }
    }

    private fun startSplash() {
        job = viewModelScope.launch {
            delay(2000L)
            val isLoggedIn = authUseCase()
            if (isLoggedIn) {
                emitSideEffect(SplashSideEffect.NavigateToHome)
            } else {
                emitSideEffect(SplashSideEffect.NavigateToMenu)
            }
        }
    }



    private fun stopSplash() {
        job?.cancel()
    }
}
