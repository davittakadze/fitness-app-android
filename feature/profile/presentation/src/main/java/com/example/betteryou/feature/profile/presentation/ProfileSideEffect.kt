package com.example.betteryou.feature.profile.presentation

import com.example.betteryou.presentation.common.UiText

sealed interface ProfileSideEffect {

    data class ShowError(val message: UiText): ProfileSideEffect

    //camera side effects
    data object OpenGallery : ProfileSideEffect
    data object OpenCamera : ProfileSideEffect
    data object RequestCameraPermission : ProfileSideEffect

    //calendar side effects

}
