package com.example.betteryou.feature.profile.presentation

import com.example.betteryou.presentation.common.UiText

sealed interface ProfileSideEffect {
    data object OnBackClick: ProfileSideEffect
    data class ShowError(val message: UiText): ProfileSideEffect

    //camera side effects
    data object OpenGallery : ProfileSideEffect
    data object OpenCamera : ProfileSideEffect
    data object RequestCameraPermission : ProfileSideEffect



}
