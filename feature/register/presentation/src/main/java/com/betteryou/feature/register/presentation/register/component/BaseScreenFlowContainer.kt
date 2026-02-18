package com.betteryou.feature.register.presentation.register.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.betteryou.feature.register.presentation.register.common.RegisterEvent
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer

@Composable
fun BaseScreenFlowContainer(
    onEvent: (RegisterEvent) -> Unit,
    spaceBetween: Int = 40,
    spaceBetweenIcon: Int = 1,
    @StringRes title: Int,
    @StringRes description: Int,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TBCTheme.colors.background)
            .systemBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(spaceBetween.dp)
    ) {
        Row {
            IconButton(
                onClick = {
                    onEvent(RegisterEvent.OnBackClick)
                },
                Modifier.padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = TBCTheme.colors.onBackground
                )

            }
            Spacer(modifier = Modifier.height(spaceBetweenIcon.dp))

        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(title),
                style = TBCTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = TBCTheme.colors.textPrimary
            )

            Spacer(modifier = Modifier.height(Spacer.spacing16))

            Text(
                text = stringResource(description),
                style = TBCTheme.typography.bodyMedium,
                color = TBCTheme.colors.textSecondary
            )
        }

        content()
    }
}