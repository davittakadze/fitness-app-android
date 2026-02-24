package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi

@Composable
fun ConsumedProductItem(item: UserDailyProductUi, onRemove: (UserDailyProductUi) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .heightIn(min = 180.dp, max = 220.dp)
            .border(shape = Radius.radius12, width = 1.dp, color = TBCTheme.colors.border)
            .clip(Radius.radius12)
            .padding(12.dp),
    ) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            TBCAppAsyncImage(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape),
                img = item.photo,
                placeholder = R.drawable.icon_workout_screen
            )
            Spacer(Modifier.width(Spacer.spacing16))
            Column(
                Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
            ) {

                Text(
                    text = item.name,
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )

                Spacer(Modifier.height(Spacer.spacing16))

                Text(
                    text = stringResource(R.string.intake, item.quantity),
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )

                Spacer(Modifier.height(Spacer.spacing8))

                Text(
                    text = stringResource(R.string.calories_intake, item.calories),
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )

                Spacer(Modifier.height(Spacer.spacing8))

                Text(
                    text = stringResource(R.string.protein_intake, item.protein),
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )

                Spacer(Modifier.height(Spacer.spacing8))

                Text(
                    text = stringResource(R.string.fats_intake, item.fat),
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )

                Spacer(Modifier.height(Spacer.spacing8))

                Text(
                    text = stringResource(R.string.carbs_intake, item.carbs),
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = {
                    onRemove(item)
                }, modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Top)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus_svgrepo_com),
                    contentDescription = null,
                    tint = TBCTheme.colors.onBackground
                )
            }
        }
    }
}