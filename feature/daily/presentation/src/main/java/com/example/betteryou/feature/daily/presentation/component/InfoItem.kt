package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer

//micro chart element
@Composable
fun InfoItem(
    color: Color,
    text: String,
    goal: Double,
    current: Double,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Box(
                Modifier
                    .size(16.dp)
                    .clip(Radius.radius32)
                    .background(color)
            )
            Spacer(Modifier.width(Spacer.spacing4))
            Text(
                text = text,
                style = LocalTBCTypography.current.bodyLarge,
                color = LocalTBCColors.current.onBackground
            )
        }
        Spacer(Modifier.height(Spacer.spacing8))
        Text(
            text = stringResource(R.string.intake, current),
            style = LocalTBCTypography.current.bodyLarge,
            color = LocalTBCColors.current.onBackground
        )
        Spacer(Modifier.height(Spacer.spacing8))
        Text(
            text = stringResource(R.string.intake, goal),
            style = LocalTBCTypography.current.bodyMedium,
            color = LocalTBCColors.current.onBackground
        )
    }
}