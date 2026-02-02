package com.example.betteryou.presentation.screen.profile

import android.net.Uri
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :
    BaseViewModel<ProfileState, ProfileEvent, ProfileSideEffect>(ProfileState()) {
    private var pendingCameraUri: Uri? = null

    override fun onEvent(event: ProfileEvent) {
        when (event) {

            ProfileEvent.OnEditProfilePictureClick -> {
                updateState {
                    copy(showImagePickerDialog = true)
                }
            }

            ProfileEvent.OnCameraSelected -> {
                updateState {
                    copy(showImagePickerDialog = false)
                }
                emitSideEffect(ProfileSideEffect.RequestCameraPermission)
            }

            ProfileEvent.OnCameraPermissionGranted -> {
                emitSideEffect(ProfileSideEffect.OpenCamera)
            }

            is ProfileEvent.OnCameraUriPrepared -> {
                pendingCameraUri = event.uri
            }

            is ProfileEvent.OnImageSelected -> {
                val finalUri = pendingCameraUri ?: event.uri
                pendingCameraUri = null

                updateState {
                    copy(profilePhotoUrl = finalUri)
                }
            }

            ProfileEvent.OnGallerySelected -> {
                updateState {
                    copy(showImagePickerDialog = false)
                }
                emitSideEffect(ProfileSideEffect.OpenGallery)
            }

            ProfileEvent.OnDialogDismiss -> {
                updateState {
                    copy(showImagePickerDialog = false)
                }
            }
            is ProfileEvent.OnFirstNameChanged -> {
                updateState { copy(firstName = event.value) }
            }
            is ProfileEvent.OnLastNameChanged -> {
                updateState { copy(lastName = event.value) }
            }

        }
    }
}
