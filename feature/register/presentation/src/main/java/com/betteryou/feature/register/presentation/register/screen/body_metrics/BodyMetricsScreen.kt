package com.betteryou.feature.register.presentation.register.screen.body_metrics

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.register.presentation.register.common.RegisterEvent
import com.betteryou.feature.register.presentation.register.common.RegisterSideEffect
import com.betteryou.feature.register.presentation.register.common.RegisterState
import com.betteryou.feature.register.presentation.register.common.RegisterViewModel
import com.betteryou.feature.register.presentation.register.component.BaseScreenFlowContainer
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.util.Spacer
import com.example.betteryou.core_ui.util.components.TBCAppButton
import com.example.betteryou.core_ui.util.components.TBCAppTextField
import com.example.betteryou.presentation.extensions.CollectSideEffects

@Composable
fun BodyMetricsScreen(
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

    BodyMetricsContent(
        state = state,
        onEvent = onEvent,
        onNext = onNext
    )
}

@Composable
private fun BodyMetricsContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
) {
    BaseScreenFlowContainer(
        onEvent = onEvent,
        title = R.string.body_screen_title,
        description = R.string.body_screen_description,
        spaceBetween = 10,
        spaceBetweenIcon = 80
    ) {

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        // Height
        TBCAppTextField(
            value = state.height,
            onValueChange = {
                onEvent(RegisterEvent.OnHeightChange(it))
            },
            placeholder = stringResource(R.string.height),
            keyboardType = KeyboardType.Number
        )

        // Weight
        TBCAppTextField(
            value = state.weight,
            onValueChange = {
                onEvent(RegisterEvent.OnWeightChange(it))
            },
            placeholder = stringResource(R.string.weight),
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        val isSelected = state.height.isNotBlank() && state.weight.isNotBlank()

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