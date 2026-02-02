package com.example.homework3.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homework3.R
import com.example.homework3.ui.theme.local_theme.LocalTBCColors

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onNavigateMenu: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.onEvent(SplashEvent.OnStartSplash)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onEvent(SplashEvent.OnStopSplash)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SplashSideEffect.NavigateToHome -> onNavigateHome()
                SplashSideEffect.NavigateToMenu-> onNavigateMenu()
            }
        }
    }

    SplashContent()
}

@Composable
fun SplashContent(){
    Box(
        modifier = Modifier.fillMaxSize().background(LocalTBCColors.current.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreen(onNavigateHome = {}, onNavigateMenu = {})
}
