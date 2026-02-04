package com.example.betteryou.feature.profile.presentation

import android.net.Uri

data class ProfileState(
    val profilePhotoUrl: Uri? = null,
    val showImagePickerDialog: Boolean = false,
    val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val isCalendarOpen:Boolean=false
)
