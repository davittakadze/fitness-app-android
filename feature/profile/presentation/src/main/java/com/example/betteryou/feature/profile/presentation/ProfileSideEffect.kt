package com.example.betteryou.feature.profile.presentation

sealed interface ProfileSideEffect {

    data class ShowError(val message:String): ProfileSideEffect

    //camera side effects
    data object OpenGallery : ProfileSideEffect
    data object OpenCamera : ProfileSideEffect
    data object RequestCameraPermission : ProfileSideEffect

    //calendar side effects

}
