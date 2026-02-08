package com.example.betteryou.feature.profile.presentation

import android.net.Uri
import com.example.betteryou.feature.profile.presentation.model.Sex
import java.time.LocalDate
import java.time.YearMonth

data class ProfileState(
    //profile picture state
    val profilePhoto: Uri? = null,
    val profilePhotoUrl: String? = null,
    val showImagePickerDialog: Boolean = false,

    val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val height: String = "",
    val weight: String = "",
    val selectedSex: Sex? = null,
    val age:Int?=null,

    //calendar state
    val isCalendarOpen: Boolean = false,
    val calendarMonth: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate? = null,
    val isYearPickerOpen: Boolean = false,
    val isMonthPickerOpen: Boolean = false
)
