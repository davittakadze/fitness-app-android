package com.example.betteryou.presentation.screen.profile

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.betteryou.R
import com.example.betteryou.ui.theme.util.Radius
import com.example.betteryou.ui.theme.util.Spacer
import com.example.betteryou.ui.theme.TBCTheme
import com.example.betteryou.ui.theme.local_theme.LocalTBCColors
import com.example.betteryou.ui.theme.local_theme.LocalTBCTypography
import com.example.betteryou.ui.theme.util.components.AppButtonType
import com.example.betteryou.ui.theme.util.components.DatePickerRow
import com.example.betteryou.ui.theme.util.components.TBCAppButton
import com.example.betteryou.ui.theme.util.components.ThinTBCAppTextField
import java.io.File

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                viewModel.onEvent(ProfileEvent.OnImageSelected(Uri.EMPTY))
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                viewModel.onEvent(ProfileEvent.OnCameraPermissionGranted)
            }
        }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                viewModel.onEvent(ProfileEvent.OnImageSelected(it))
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
                    viewModel.onEvent(ProfileEvent.OnCameraUriPrepared(uri))
                    cameraLauncher.launch(uri)
                }

                ProfileSideEffect.RequestCameraPermission -> {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }
    }

    ProfileContent(state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(LocalTBCColors.current.background)
            .padding(
                top = Spacer.spacing64,
                start = Spacer.spacing24,
                end = Spacer.spacing24,
                bottom = Spacer.spacing48
            )
    ) {
        Spacer(modifier = Modifier.height(Spacer.spacing16))

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
                    .height(500.dp),

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
                        .padding(vertical = 64.dp, horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp)
                            .clickable { onEvent(ProfileEvent.OnEditProfilePictureClick) },
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
                            onValueChange = { onEvent(ProfileEvent.OnFirstNameChanged(it)) },
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
                            onValueChange = { onEvent(ProfileEvent.OnFirstNameChanged(it)) },
                            placeholder = stringResource(R.string.last_name)
                        )
                    }
                    Spacer(modifier = Modifier.height(Spacer.spacing16))

                    DatePickerRow(
                        label = "Date of birth",
                        valueText = "14/11/2006",
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.height(Spacer.spacing32))

                    SexSelector(
                        selected = Sex.FEMALE,
                        onSelected = { }
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

                            Spacer(modifier = Modifier.height(Spacer.spacing8))

                            ThinTBCAppTextField(
                                value = "",
                                onValueChange = { },
                                placeholder = stringResource(R.string.height_placeholder),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.weight),
                                style = LocalTBCTypography.current.bodyMedium,
                                color = LocalTBCColors.current.onBackground
                            )

                            Spacer(modifier = Modifier.height(Spacer.spacing8))

                            ThinTBCAppTextField(
                                value = "",
                                onValueChange = { },
                                placeholder = stringResource(R.string.weight_placeholder),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }


                }
            }
            AsyncImage(
                model = state.profilePhotoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.mipmap.user),
                error = painterResource(R.mipmap.user),
                fallback = painterResource(R.mipmap.user),
                modifier = Modifier
                    .size(Spacer.spacing100)
                    .offset(0.dp, (-240).dp)
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

            }
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        TBCAppButton(
            text = stringResource(R.string.log_out),
            type = AppButtonType.Outlined,
            modifier = Modifier.fillMaxWidth(),
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        TBCAppButton(
            text = stringResource(R.string.delete_account),
            modifier = Modifier.fillMaxWidth(),
            type = AppButtonType.Outlined,
            onClick = {

            }
        )
    }

    if (state.showImagePickerDialog) {
        ImagePickerBottomSheet(
            onGalleryClick = {
                onEvent(ProfileEvent.OnGallerySelected)
            },
            onCameraClick = {
                onEvent(ProfileEvent.OnCameraSelected)
            },
            onDismiss = {
                onEvent(ProfileEvent.OnDialogDismiss)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerBottomSheet(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacer.spacing16)
        ) {

            BottomSheetItem(
                text = "Choose from gallery",
                onClick = onGalleryClick
            )

            BottomSheetItem(
                text = "Take photo",
                onClick = onCameraClick
            )

            BottomSheetItem(
                text = "Cancel",
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
        style = MaterialTheme.typography.bodyLarge
    )
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

enum class Sex {
    MALE, FEMALE
}

@Composable
fun SexSelector(
    selected: Sex,
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

//@Preview
@Composable
fun ProfileScreenPreview() {
    TBCTheme {
        ProfileContent(ProfileState(), { })
    }
}