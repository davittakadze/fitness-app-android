package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.daily.presentation.model.ProductUi

@Composable
fun TBCAppDropdownItem(item: ProductUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TBCAppAsyncImage(
            img = item.photo,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            placeholder = R.drawable.icon_workout_screen
        )

        Spacer(modifier = Modifier.width(Spacer.spacing12))

        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyMedium,
            color = TBCTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.right_arrow_svgrepo_com),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = TBCTheme.colors.onBackground
        )
    }
}