package com.example.homework3.presentation.screen.profile

import android.net.Uri

data class ProfileState(
    val profilePhotoUrl: Uri? = null,
    val showImagePickerDialog: Boolean = false,
    val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = ""
)
