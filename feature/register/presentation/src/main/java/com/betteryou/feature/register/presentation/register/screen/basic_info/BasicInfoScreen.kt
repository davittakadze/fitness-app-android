package com.betteryou.feature.register.presentation.register.screen.basic_info

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.register.domain.model.Gender
import com.betteryou.feature.register.presentation.register.common.RegisterEvent
import com.betteryou.feature.register.presentation.register.common.RegisterSideEffect
import com.betteryou.feature.register.presentation.register.common.RegisterState
import com.betteryou.feature.register.presentation.register.common.RegisterViewModel
import com.betteryou.feature.register.presentation.register.component.BaseScreenFlowContainer
import com.betteryou.feature.register.presentation.register.mapper.toResourceString
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
import com.example.betteryou.presentation.extensions.CollectSideEffects

@Composable
fun BasicInfoScreen(
    viewModel: RegisterViewModel,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is RegisterSideEffect.NavigateBack -> {
                onBack()
            }

            is RegisterSideEffect.NavigateToHome -> Unit
            is RegisterSideEffect.ShowError -> Unit
        }
    }

    BasicInfoContent(
        state = state,
        onEvent = onEvent,
        onNext = onNext
    )
}

@Composable
private fun BasicInfoContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
) {
    BaseScreenFlowContainer(
        onEvent = onEvent,
        title = R.string.basic_screen_title,
        description = R.string.basic_screen_description,
        spaceBetween = 10,
        spaceBetweenIcon = 80
    ) {

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        // Name
        TBCAppTextField(
            value = state.name,
            onValueChange = {
                onEvent(RegisterEvent.OnNameChange(it))
            },
            placeholder = stringResource(R.string.your_last_name),
            keyboardType = KeyboardType.Text
        )

        //Last name
        TBCAppTextField(
            value = state.lastName,
            onValueChange = {
                onEvent(RegisterEvent.OnLastNameChange(it))
            },
            placeholder = stringResource(R.string.last_name),
            keyboardType = KeyboardType.Text
        )

        // Age
        TBCAppTextField(
            value = state.age,
            onValueChange = {
                onEvent(RegisterEvent.OnAgeChange(it))
            },
            placeholder = stringResource(R.string.age),
            keyboardType = KeyboardType.Number,
            numbersOnly = true
        )

        // Gender
        GenderDropDown(
            state = state,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(Spacer.spacing32))

        val isSelected =
            state.name.isNotBlank() && state.age.isNotBlank() && state.gender != Gender.UNSPECIFIED

        // Next button
        TBCAppButton(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(R.string.next),
            enabled = isSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenderDropDown(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf(Gender.MALE, Gender.FEMALE)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth()
    ) {
        TBCAppTextField(
            value = stringResource(state.gender.toResourceString()),
            onValueChange = {},
            placeholder = "",
            readOnly = true,
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genderOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = selectionOption.name.lowercase()
                                .replaceFirstChar { it.uppercase() },
                            style = TBCTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        onEvent(RegisterEvent.OnGenderChange(selectionOption))
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

