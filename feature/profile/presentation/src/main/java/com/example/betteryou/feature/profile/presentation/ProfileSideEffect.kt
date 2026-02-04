package com.example.betteryou.feature.profile.presentation

sealed interface ProfileSideEffect {
    data object OpenGallery : ProfileSideEffect
    data object OpenCamera : ProfileSideEffect
    data object RequestCameraPermission : ProfileSideEffect
}
