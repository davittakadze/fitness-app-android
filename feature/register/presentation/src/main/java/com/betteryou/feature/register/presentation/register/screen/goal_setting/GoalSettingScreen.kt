package com.betteryou.feature.register.presentation.register.screen.goal_setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.register.domain.model.GoalType
import com.betteryou.feature.register.presentation.register.common.RegisterEvent
import com.betteryou.feature.register.presentation.register.common.RegisterSideEffect
import com.betteryou.feature.register.presentation.register.common.RegisterState
import com.betteryou.feature.register.presentation.register.common.RegisterViewModel
import com.betteryou.feature.register.presentation.register.component.BaseScreenFlowContainer
import com.betteryou.feature.register.presentation.register.component.SelectCard
import com.betteryou.feature.register.presentation.register.mapper.toResourceString
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.presentation.extensions.CollectSideEffects

@Composable
fun GoalSettingScreen(
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

    GoalSettingContent(
        state = state,
        onEvent = onEvent,
        onNext = onNext,
    )
}

@Composable
private fun GoalSettingContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
) {
    BaseScreenFlowContainer(
        onEvent = onEvent,
        title = R.string.goal_screen_title,
        description = R.string.goal_screen_description,
        spaceBetween = 10,
        spaceBetweenIcon = 80
    ) {

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        Column(verticalArrangement = Arrangement.spacedBy(Spacer.spacing12)) {
            GoalType.entries.forEach { type ->
                SelectCard(
                    type = type.toResourceString(),
                    isSelected = state.goal == type,
                    onClick = { onEvent(RegisterEvent.OnGoalTypeChange(type)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        val isSelected = state.goal != null

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