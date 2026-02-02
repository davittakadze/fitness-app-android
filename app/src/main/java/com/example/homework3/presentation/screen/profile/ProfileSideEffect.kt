package com.example.homework3.presentation.screen.profile

sealed interface ProfileSideEffect {
    data object OpenGallery : ProfileSideEffect
    data object OpenCamera : ProfileSideEffect
    data object RequestCameraPermission : ProfileSideEffect
}
