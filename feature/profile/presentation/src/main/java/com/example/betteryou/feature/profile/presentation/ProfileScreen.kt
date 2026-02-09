package com.example.betteryou.feature.profile.presentation

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Spacer
import java.io.File
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.components.calendar.CalendarContent
import com.example.betteryou.feature.profile.presentation.ProfileEvent.*
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.components.AppButtonType
import com.example.betteryou.core_ui.util.components.DatePickerRow
import com.example.betteryou.core_ui.util.components.TBCAppButton
import com.example.betteryou.core_ui.util.components.TBCAppCircularProgress
import com.example.betteryou.core_ui.util.components.ThinTBCAppTextField
import com.example.betteryou.core_ui.util.components.calendar.MonthPicker
import com.example.betteryou.core_ui.util.components.calendar.YearPickerDialog
import java.time.LocalDate
import com.example.betteryou.feature.profile.presentation.model.Sex
import com.example.betteryou.feature.profile.presentation.model.UserUi
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ProfileScreen(
    onBackClick:()->Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.onEvent(OnImageSelected(Uri.EMPTY))
            }
        }


    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                viewModel.onEvent(OnCameraPermissionGranted)
            }
        }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                viewModel.onEvent(OnImageSelected(it))
            }
        }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                ProfileSideEffect.OpenGallery -> {
                    imagePickerLauncher.launch("image/*")
                }

                ProfileSideEffect.OpenCamera -> {
                    val uri = createImageUri(context)
                    viewModel.onEvent(OnCameraUriPrepared(uri))
                    cameraLauncher.launch(uri)
                }

                ProfileSideEffect.RequestCameraPermission -> {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }

                is ProfileSideEffect.ShowError -> {
                    SnackBarController.sendEvent(
                        SnackbarEvent(
                            message = effect.message.asString(context)
                        )
                    )
                }

                ProfileSideEffect.OnBackClick -> {
                    onBackClick()
                }
            }
        }
    }

    ProfileContent(state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {
    val focusManager = LocalFocusManager.current
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(LocalTBCColors.current.background)
                .padding(
                    top = Spacer.spacing48,
                    start = Spacer.spacing24,
                    end = Spacer.spacing24,
                    bottom = Spacer.spacing48
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                }
        ) {
            IconButton(
                onClick = {
                    onEvent(OnBackClick)
                },
                Modifier.padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = LocalTBCColors.current.onBackground
                )
            }

            Spacer(modifier = Modifier.height(Spacer.spacing12))

            Text(
                text = stringResource(R.string.profile),
                style = LocalTBCTypography.current.headlineLarge,
                color = LocalTBCColors.current.onBackground
            )

            Spacer(modifier = Modifier.height(Spacer.spacing32))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(530.dp),

                    shape = Radius.radius16,

                    colors = CardDefaults.cardColors(
                        containerColor = LocalTBCColors.current.surface
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 64.dp, horizontal = 24.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp)
                                .clickable { onEvent(OnEditProfilePictureClick) },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.edit_profile_picture),
                                style = LocalTBCTypography.current.bodyLarge,
                                color = LocalTBCColors.current.accent
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacer.spacing16))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.first_name),
                                style = LocalTBCTypography.current.bodyMedium,
                                color = LocalTBCColors.current.onBackground
                            )

                            Spacer(modifier = Modifier.width(Spacer.spacing16))

                            ThinTBCAppTextField(
                                value = state.firstName,
                                onValueChange = { onEvent(OnFirstNameChanged(it)) },
                                placeholder = stringResource(R.string.first_name)
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacer.spacing16))

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                stringResource(R.string.last_name),
                                style = LocalTBCTypography.current.bodyMedium,
                                color = LocalTBCColors.current.onBackground
                            )

                            Spacer(modifier = Modifier.width(Spacer.spacing16))

                            ThinTBCAppTextField(
                                value = state.lastName,
                                onValueChange = { onEvent(OnLastNameChanged(it)) },
                                placeholder = stringResource(R.string.last_name)
                            )
                        }
                        Spacer(modifier = Modifier.height(Spacer.spacing24))

                        DatePickerRow(
                            label = stringResource(R.string.date_of_birth),
                            valueText = state.selectedDate?.formatToString(),
                            onClick = { onEvent(OnOpenCalendar) }
                        )

                        if (state.isCalendarOpen) {
                            CalendarBottomSheet(
                                onDismiss = {
                                    onEvent(OnCalendarDismiss)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(Spacer.spacing32))

                        SexSelector(
                            selected = state.selectedSex,
                            onSelected = {
                                onEvent(OnSexSelected(it))
                            }
                        )

                        Spacer(modifier = Modifier.height(Spacer.spacing32))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = stringResource(R.string.height),
                                    style = LocalTBCTypography.current.bodyMedium,
                                    color = LocalTBCColors.current.onBackground
                                )

                                Spacer(modifier = Modifier.height(Spacer.spacing16))

                                ThinTBCAppTextField(
                                    value = state.height,
                                    onValueChange = { onEvent(OnHeightChanged(it)) },
                                    placeholder = stringResource(R.string.height_placeholder),
                                    modifier = Modifier.fillMaxWidth(),
                                    numbersOnly = true
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = stringResource(R.string.weight),
                                    style = LocalTBCTypography.current.bodyMedium,
                                    color = LocalTBCColors.current.onBackground
                                )

                                Spacer(modifier = Modifier.height(Spacer.spacing16))

                                ThinTBCAppTextField(
                                    value = state.weight,
                                    onValueChange = { onEvent(OnWeightChanged(it)) },
                                    placeholder = stringResource(R.string.weight_placeholder),
                                    modifier = Modifier.fillMaxWidth(),
                                    numbersOnly = true
                                )
                            }
                        }

                    }
                }

                AsyncImage(
                    model = state.profilePhoto,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.mipmap.user),
                    error = painterResource(R.mipmap.user),
                    fallback = painterResource(R.mipmap.user),
                    modifier = Modifier
                        .size(Spacer.spacing100)
                        .offset(0.dp, (-255).dp)
                        .clip(CircleShape)
                        .border(
                            3.dp,
                            LocalTBCColors.current.accent,
                            CircleShape
                        )
                )
            }

            Spacer(modifier = Modifier.height(Spacer.spacing24))


            TBCAppButton(
                text = stringResource(R.string.save_changes),
                type = AppButtonType.Outlined,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEvent(
                        SaveChanges(
                            UserUi(
                                state.firstName,
                                state.lastName,
                                age = state.selectedDate?.let { calculateAge(it) } ?: 0,
                                state.selectedSex,
                                state.height.toFloatOrNull(),
                                state.weight.toFloatOrNull(),
                                photoUrl = state.profilePhoto
                            )
                        )
                    )
                }
            )
        }
        if (state.isLoading) {
            TBCAppCircularProgress(Modifier.align(Alignment.Center))
        }
    }

    if (state.showImagePickerDialog) {
        ImagePickerBottomSheet(
            onGalleryClick = {
                onEvent(OnGallerySelected)
            },
            onCameraClick = {
                onEvent(OnCameraSelected)
            },
            onDismiss = {
                onEvent(OnDialogDismiss)
            }
        )
    }

    if (state.isCalendarOpen) {
        CalendarBottomSheet(
            state = state,
            onEvent = onEvent,
            onDismiss = { onEvent(OnCalendarDismiss) }
        )
    }

    if (state.isMonthPickerOpen) {
        MonthPicker(
            currentMonth = state.calendarMonth.monthValue,
            onMonthSelected = { month ->
                onEvent(OnMonthSelected(month))
            },
            onDismiss = {
                onEvent(OnMonthPickerToggle)
            }
        )
    }

    if (state.isYearPickerOpen) {
        YearPickerDialog(
            currentYear = state.calendarMonth.year,
            onYearSelected = { year ->
                onEvent(OnYearSelected(year))
            },
            onDismiss = {
                onEvent(OnYearPickerToggle)
            }
        )
    }
}


@Composable
fun SexSelector(
    selected: Sex?,
    onSelected: (Sex) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SexItem(
            text = "Male",
            selected = selected == Sex.MALE,
            onClick = { onSelected(Sex.MALE) },
            modifier = Modifier.weight(1f)
        )

        SexItem(
            text = "Female",
            selected = selected == Sex.FEMALE,
            onClick = { onSelected(Sex.FEMALE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SexItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background =
        if (selected) LocalTBCColors.current.onBackground
        else Color.Transparent

    val textColor =
        if (selected) LocalTBCColors.current.background
        else LocalTBCColors.current.onBackground

    val borderColor =
        if (selected) Color.Transparent
        else LocalTBCColors.current.border

    Box(
        modifier = modifier
            .height(44.dp)
            .background(background, CircleShape)
            .border(1.dp, borderColor, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = LocalTBCTypography.current.bodyLarge,
            color = textColor
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = LocalTBCColors.current.background
    ) {
        CalendarContent(
            month = state.calendarMonth,
            selectedDate = state.selectedDate,
            onPrevMonth = { onEvent(OnPrevMonth) },
            onNextMonth = { onEvent(OnNextMonth) },
            onDateSelected = { onEvent(OnDateSelected(it, calculateAge(it))) },
            onYearClick = {
                onEvent(OnYearPickerToggle)
            },
            onMonthClick = {
                onEvent(OnMonthPickerToggle)
            }
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    TBCTheme {
        ProfileContent(ProfileState()) {}
    }
}


//util module-shi gasatani!
fun LocalDate.formatToString(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.month.ordinal + 1
    val monthStr = month.toString().padStart(2, '0')
    val year = this.year.toString()

    return "$day/$monthStr/$year"
}

fun calculateAge(birthDate: LocalDate): Int {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    var age = today.year - birthDate.year

    if (
        today.month < birthDate.month ||
        (today.month == birthDate.month &&
                today.dayOfMonth < birthDate.dayOfMonth)
    ) {
        age--
    }

    return age
}

fun createImageUri(context: Context): Uri {
    val file = File(
        context.cacheDir,
        "camera_${System.currentTimeMillis()}.jpg"
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerBottomSheet(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = LocalTBCColors.current.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacer.spacing16)
        ) {

            BottomSheetItem(
                text = stringResource(R.string.choose_from_gallery),
                onClick = onGalleryClick
            )

            BottomSheetItem(
                text = stringResource(R.string.take_photo),
                onClick = onCameraClick
            )

            BottomSheetItem(
                text = stringResource(R.string.cancel),
                onClick = onDismiss
            )

            Spacer(modifier = Modifier.height(Spacer.spacing8))
        }
    }
}

@Composable
fun BottomSheetItem(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                vertical = Spacer.spacing16,
                horizontal = Spacer.spacing24
            ),
        style = MaterialTheme.typography.bodyLarge,
        color = LocalTBCColors.current.onBackground
    )
}