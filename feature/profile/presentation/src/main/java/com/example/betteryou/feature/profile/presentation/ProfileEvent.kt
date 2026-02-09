package com.example.betteryou.feature.profile.presentation

import android.net.Uri
import com.example.betteryou.feature.profile.presentation.model.Sex
import com.example.betteryou.feature.profile.presentation.model.UserUi
import java.time.LocalDate

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

    data class OnHeightChanged(val value: String) : ProfileEvent

    data class OnWeightChanged(val value: String) : ProfileEvent

    data class OnSexSelected(val sex: Sex): ProfileEvent

    //calendar events
    data object OnOpenCalendar : ProfileEvent
    data object OnCalendarDismiss : ProfileEvent

    data object OnPrevMonth : ProfileEvent
    data object OnNextMonth : ProfileEvent

    data class OnDateSelected(val date: LocalDate,val calculatedAge :Int) : ProfileEvent

    data object OnYearPickerToggle : ProfileEvent

    data class OnYearSelected(val year: Int) : ProfileEvent

    data object OnMonthPickerToggle : ProfileEvent
    data class OnMonthSelected(val month: Int) : ProfileEvent

    //upload events
    data class SaveChanges(val user: UserUi): ProfileEvent

    data object OnBackClick:ProfileEvent
}