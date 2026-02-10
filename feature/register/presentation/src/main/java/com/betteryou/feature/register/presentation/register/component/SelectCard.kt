package com.betteryou.feature.register.presentation.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.TBCTheme

@Composable
fun SelectCard(
    type: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = TBCTheme.colors.surface,
        border = BorderStroke(
            width = 2.dp,
            color = if (isSelected) TBCTheme.colors.accent else TBCTheme.colors.border
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(type),
                style = TBCTheme.typography.bodyLarge,
                color = if (isSelected) TBCTheme.colors.textPrimary else TBCTheme.colors.textSecondary
            )

            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = TBCTheme.colors.accent,
                    unselectedColor = TBCTheme.colors.textSecondary
                )
            )
        }
    }
}