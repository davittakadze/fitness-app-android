package com.example.homework3.presentation.screen.profile

import android.net.Uri

sealed interface ProfileEvent {
    data object OnEditProfilePictureClick : ProfileEvent
    object OnCameraSelected : ProfileEvent
    data object OnGallerySelected : ProfileEvent
    data object OnDialogDismiss: ProfileEvent
    data object OnCameraPermissionGranted : ProfileEvent
    data class OnCameraUriPrepared(val uri: Uri) : ProfileEvent

    data class OnImageSelected(val uri: Uri) : ProfileEvent
    data class OnFirstNameChanged(val value: String) : ProfileEvent
    data class OnLastNameChanged(val value: String) : ProfileEvent
}