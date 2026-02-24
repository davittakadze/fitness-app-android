package com.example.betteryou.feature.profile.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.DatePickerRow
import com.example.betteryou.core_ui.components.text_field.ThinTBCAppTextField
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.feature.profile.presentation.CalendarBottomSheet
import com.example.betteryou.feature.profile.presentation.ProfileEvent
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnCalendarDismiss
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnEditProfilePictureClick
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnFirstNameChanged
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnHeightChanged
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnLastNameChanged
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnOpenCalendar
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnSexSelected
import com.example.betteryou.feature.profile.presentation.ProfileEvent.OnWeightChanged
import com.example.betteryou.feature.profile.presentation.ProfileState
import com.example.betteryou.util.formatToString

@Composable
fun ProfileCard(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Spacer.spacing48)
            .wrapContentHeight(),
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
                .padding(top = 64.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
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
                valueText = state.selectedDate?.formatToString() ?: "dd/MM/yyyy",
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
}