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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Spacer
import java.io.File
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.profile.presentation.ProfileEvent.*
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.button.AppButtonType
import com.example.betteryou.core_ui.components.bottom_sheet.ImagePickerBottomSheet
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.TBCAppCircularProgress
import com.example.betteryou.core_ui.components.calendar.MonthPicker
import com.example.betteryou.core_ui.components.calendar.YearPickerDialog
import com.example.betteryou.feature.profile.presentation.component.CalendarBottomSheet
import com.example.betteryou.feature.profile.presentation.component.ProfileCard
import com.example.betteryou.feature.profile.presentation.model.UserUi
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent
import com.example.betteryou.util.calculateAge
import com.example.betteryou.util.formatToString

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
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
private fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = LocalTBCColors.current.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        style = LocalTBCTypography.current.headlineLarge,
                        color = LocalTBCColors.current.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(OnBackClick) }) {
                        Icon(
                            painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = LocalTBCColors.current.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TBCTheme.colors.background,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Box(Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(LocalTBCColors.current.background))
        {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = Spacer.spacing32,
                        start = Spacer.spacing24,
                        end = Spacer.spacing24,
                        bottom = Spacer.spacing24
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusManager.clearFocus()
                    }
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    ProfileCard(
                        state = state,
                        onEvent = onEvent
                    )

                    AsyncImage(
                        model = state.profilePhoto,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.mipmap.user),
                        error = painterResource(R.mipmap.user),
                        fallback = painterResource(R.mipmap.user),
                        modifier = Modifier
                            .size(Spacer.spacing100)
                            .align(Alignment.TopCenter)
                            .clip(CircleShape)
                            .border(
                                3.dp,
                                LocalTBCColors.current.accent,
                                CircleShape
                            )
                    )
                }

                Spacer(modifier = Modifier.height(Spacer.spacing12))


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
                                    age = state.selectedDate?.let { calculateAge(it) } ?: state.age,
                                    birthDate = state.selectedDate?.formatToString(),
                                    state.selectedSex,
                                    state.height.toDoubleOrNull(),
                                    state.weight.toDoubleOrNull(),
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
}

private fun createImageUri(context: Context): Uri {
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

@Preview
@Composable
private fun ProfileScreenPreview() {
    TBCTheme {
        ProfileContent(ProfileState()) {}
    }
}
