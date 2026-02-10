package com.betteryou.feature.register.presentation.register.screen.user_activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.register.domain.model.ActivityLevelType
import com.betteryou.feature.register.presentation.register.common.RegisterEvent
import com.betteryou.feature.register.presentation.register.common.RegisterSideEffect
import com.betteryou.feature.register.presentation.register.common.RegisterState
import com.betteryou.feature.register.presentation.register.common.RegisterViewModel
import com.betteryou.feature.register.presentation.register.component.BaseScreenFlowContainer
import com.betteryou.feature.register.presentation.register.component.SelectCard
import com.betteryou.feature.register.presentation.register.mapper.toResourceString
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.util.Spacer
import com.example.betteryou.core_ui.util.components.TBCAppButton
import com.example.betteryou.presentation.extensions.CollectSideEffects

@Composable
fun ActivityLevelScreen(
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

    UserActivityContent(
        state = state,
        onEvent = onEvent,
        onNext = onNext
    )
}

@Composable
private fun UserActivityContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNext: () -> Unit,
) {
    BaseScreenFlowContainer(
        onEvent = onEvent,
        title = R.string.activity_screen_title,
        description = R.string.activity_screen_description
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(Spacer.spacing12)) {
            ActivityLevelType.entries.forEach { type ->
                SelectCard(
                    type = type.toResourceString(),
                    isSelected = state.activityLevel == type,
                    onClick = { onEvent(RegisterEvent.OnActivityLevelChange(type)) }
                )
            }
        }

        val isSelected = state.activityLevel != null

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

