package com.example.betteryou.feature.profile.presentation

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
            //profile events
            ProfileEvent.OnEditProfilePictureClick -> {
                updateState {
                    ProfileState(showImagePickerDialog = true)
                }
            }

            ProfileEvent.OnCameraSelected -> {
                updateState {
                    ProfileState(showImagePickerDialog = false)
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
                    ProfileState(profilePhotoUrl = finalUri)
                }
            }

            ProfileEvent.OnGallerySelected -> {
                updateState {
                    ProfileState(showImagePickerDialog = false)
                }
                emitSideEffect(ProfileSideEffect.OpenGallery)
            }

            ProfileEvent.OnDialogDismiss -> {
                updateState {
                    ProfileState(showImagePickerDialog = false)
                }
            }

            //text field events
            is ProfileEvent.OnFirstNameChanged -> {
                updateState { ProfileState(firstName = event.value) }
            }
            is ProfileEvent.OnLastNameChanged -> {
                updateState { ProfileState(lastName = event.value) }
            }

            //calendar events
            ProfileEvent.OnCalendarClick -> updateState { copy(isCalendarOpen=true) }
            ProfileEvent.OnCalendarDismiss -> updateState { copy(isCalendarOpen=false) }
        }
    }
}
