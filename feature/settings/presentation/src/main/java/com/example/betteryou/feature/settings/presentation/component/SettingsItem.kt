package com.example.betteryou.feature.settings.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Spacer

@Composable
fun SettingsItem(
    title: String,
    textColor: Color,
    showArrow: Boolean = true,
    showDivider: Boolean = true,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit? = {},
) {

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            leadingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Spacer(Modifier.width(Spacer.spacing16))

            Text(
                text = title,
                color = textColor,
                style = LocalTBCTypography.current.bodyLargest,
                modifier = Modifier.weight(1f)
            )

            trailingContent?.invoke()

            if (trailingContent == null && trailingIcon != null) {
                Icon(
                    painter = trailingIcon,
                    contentDescription = null,
                    tint = LocalTBCColors.current.textSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }

            if (trailingContent == null && trailingIcon == null && showArrow) {
                Icon(
                    painter = painterResource(R.drawable.right_arrow_svgrepo_com),
                    contentDescription = null,
                    tint = LocalTBCColors.current.textSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                Modifier, 0.5.dp,
                LocalTBCColors.current.primary.copy(alpha = 0.08f)
            )
        }
    }
}