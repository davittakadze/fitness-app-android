package com.example.betteryou.feature.profile.presentation

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.domain.usecase.GetUserInfoUseCase
import com.example.betteryou.feature.domain.usecase.UploadUserInfoUseCase
import com.example.betteryou.feature.profile.presentation.mapper.toDomain
import com.example.betteryou.feature.profile.presentation.mapper.toPresentation
import com.example.betteryou.feature.profile.presentation.model.UserUi
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import java.time.YearMonth
import com.example.betteryou.core_res.R

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uploadUseCase: UploadUserInfoUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
) :
    BaseViewModel<ProfileState, ProfileEvent, ProfileSideEffect>(ProfileState()) {
    private var pendingCameraUri: Uri? = null

    init {
        viewModelScope.launch {
            getUserInfoUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { user ->
                            val userUi = user.toPresentation()
                            updateState {
                                copy(
                                    firstName = userUi.firstName.orEmpty(),
                                    lastName = userUi.lastName.orEmpty(),
                                    age = userUi.age,
                                    selectedDate = getBirthDateFromAge(userUi.age),
                                    selectedSex = userUi.gender,
                                    height = userUi.height?.toString().orEmpty(),
                                    weight = userUi.weight?.toString().orEmpty(),
                                    profilePhoto = userUi.photoUrl
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        emitSideEffect(
                            ProfileSideEffect.ShowError(
                                UiText.DynamicString(result.errorMessage)
                            )
                        )
                    }

                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }
                }
            }
        }
    }


    override fun onEvent(event: ProfileEvent) {
        when (event) {
            //profile events
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
                    copy(profilePhoto = finalUri)
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

            //text field events
            is ProfileEvent.OnFirstNameChanged -> {
                updateState { copy(firstName = event.value) }
            }

            is ProfileEvent.OnLastNameChanged -> {
                updateState { copy(lastName = event.value) }
            }

            //calendar events
            ProfileEvent.OnCalendarDismiss -> updateState {
                copy(
                    isCalendarOpen = false
                )
            }

            is ProfileEvent.OnDateSelected -> {
                if (YearMonth.from(event.date) == state.value.calendarMonth) {
                    updateState {
                        copy(
                            selectedDate = event.date
                        )
                    }
                } else {
                    this
                }
            }

            ProfileEvent.OnNextMonth -> updateState {
                copy(
                    calendarMonth = calendarMonth.plusMonths(1)
                )
            }

            ProfileEvent.OnOpenCalendar -> updateState {
                copy(
                    isCalendarOpen = true,
                    calendarMonth = YearMonth.now()
                )
            }

            ProfileEvent.OnPrevMonth -> updateState {
                copy(
                    calendarMonth = calendarMonth.minusMonths(1)
                )
            }

            is ProfileEvent.OnHeightChanged -> updateState {
                copy(height = event.value)
            }

            is ProfileEvent.OnWeightChanged -> updateState {
                copy(weight = event.value)
            }

            is ProfileEvent.OnSexSelected -> updateState {
                copy(selectedSex = event.sex)
            }

            is ProfileEvent.OnMonthPickerToggle -> updateState { copy(isMonthPickerOpen = !state.value.isMonthPickerOpen) }

            is ProfileEvent.OnMonthSelected -> updateState {
                val newMonth = state.value.calendarMonth.withMonth(event.month)
                copy(calendarMonth = newMonth)
            }

            ProfileEvent.OnYearPickerToggle -> updateState {
                copy(isYearPickerOpen = !state.value.isYearPickerOpen)
            }

            is ProfileEvent.OnYearSelected -> updateState {
                val newMonth = state.value.calendarMonth.withYear(event.year)
                copy(
                    calendarMonth = newMonth
                )
            }

            is ProfileEvent.SaveChanges -> uploadUserProfile(event.user)
        }
    }

    private fun uploadUserProfile(user: UserUi) {
        viewModelScope.launch {
            uploadUseCase(user.toDomain())
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            updateState { copy(isLoading = false) }
                            emitSideEffect(
                                ProfileSideEffect.ShowError(
                                    UiText.DynamicString(result.errorMessage)
                                )
                            )
                        }

                        is Resource.Loader -> {
                            updateState { copy(isLoading = result.isLoading) }
                        }

                        is Resource.Success -> {
                            updateState { copy(isLoading = false) }
                            emitSideEffect(
                                ProfileSideEffect.ShowError(
                                    UiText.StringResource(R.string.profile_updated)
                                )
                            )
                        }
                    }
                }
        }
    }

    fun getBirthDateFromAge(age: Int?): LocalDate {
        return LocalDate.now().minusYears(age!!.toLong())
    }
}
