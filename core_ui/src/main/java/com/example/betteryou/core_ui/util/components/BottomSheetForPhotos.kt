package com.example.betteryou.core_ui.util.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.util.Spacer


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