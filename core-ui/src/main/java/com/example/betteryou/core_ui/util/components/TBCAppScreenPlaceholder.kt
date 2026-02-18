package com.example.betteryou.core_ui.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Spacer

@Composable
fun TBCAppScreenPlaceholder(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String,
    icon: Int,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = TBCTheme.colors.textSecondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = TBCTheme.colors.accent
            )
        }

        Spacer(modifier = Modifier.height(Spacer.spacing24))

        Text(
            text = primaryText,
            style = TBCTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TBCTheme.colors.textPrimary
        )

        Spacer(modifier = Modifier.height(Spacer.spacing8))

        Text(
            text = secondaryText,
            style = TBCTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = TBCTheme.colors.textSecondary
        )
    }
}