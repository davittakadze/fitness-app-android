package com.example.betteryou.core_ui.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer

@Composable
fun DatePickerRow(
    modifier: Modifier = Modifier,
    label: String,
    valueText: String?,
    placeholder: String = "DD/MM/YYYY",
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val isEmpty = valueText.isNullOrBlank()

    val borderColor =
        if (isPressed) LocalTBCColors.current.accent
        else LocalTBCColors.current.border

    val containerColor =
        if (isEmpty) LocalTBCColors.current.surface.copy(alpha = 0.65f)
        else LocalTBCColors.current.surface

    val valueColor =
        if (isEmpty) LocalTBCColors.current.textSecondary
        else LocalTBCColors.current.onBackground

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(containerColor, Radius.radius12)
            .border(Spacer.spacing1, borderColor, Radius.radius12)
            .clickable(
                onClick = onClick,
                onClickLabel = "Pick date"
            )
            .padding(horizontal = Spacer.spacing16),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = LocalTBCTypography.current.bodyLarge,
                color = LocalTBCColors.current.textSecondary
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (isEmpty) placeholder else valueText,
                style = LocalTBCTypography.current.bodyLarge,
                color = valueColor
            )

            Spacer(modifier = Modifier.width(Spacer.spacing12))

            Icon(
                painter = painterResource(R.drawable.calendar_svgrepo_com),
                contentDescription = null,
                tint = valueColor.copy(alpha = if (isEmpty) 0.7f else 1f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
