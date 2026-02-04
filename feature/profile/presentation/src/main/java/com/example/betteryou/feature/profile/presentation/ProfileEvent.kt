package com.example.betteryou.feature.profile.presentation

import android.net.Uri

sealed interface ProfileEvent {
    //profile picture events
    data object OnEditProfilePictureClick : ProfileEvent
    object OnCameraSelected : ProfileEvent
    data object OnGallerySelected : ProfileEvent
    data object OnDialogDismiss: ProfileEvent
    data object OnCameraPermissionGranted : ProfileEvent
    data class OnCameraUriPrepared(val uri: Uri) : ProfileEvent

    data class OnImageSelected(val uri: Uri) : ProfileEvent

    //text field events
    data class OnFirstNameChanged(val value: String) : ProfileEvent
    data class OnLastNameChanged(val value: String) : ProfileEvent

    //calendar events
    data object OnCalendarClick: ProfileEvent
    data object OnCalendarDismiss : ProfileEvent
}