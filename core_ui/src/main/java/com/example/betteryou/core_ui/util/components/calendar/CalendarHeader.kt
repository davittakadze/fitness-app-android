package com.example.betteryou.core_ui.util.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Spacer
import java.time.YearMonth

@Composable
fun CalendarHeader(
    month: YearMonth,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onYearClick: () -> Unit,
    onMonthClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = month.month.name.lowercase().replaceFirstChar { it.uppercase() },
                style = LocalTBCTypography.current.bodyLarge,
                color = LocalTBCColors.current.onBackground,
                modifier = Modifier.clickable {
                    onMonthClick()
                }
            )


            Spacer(modifier = Modifier.size(Spacer.spacing8))


            Text(
                text = month.year.toString(),
                style = LocalTBCTypography.current.bodyLarge,
                color = LocalTBCColors.current.onBackground,
                modifier = Modifier.clickable { onYearClick() }
            )
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                contentDescription = stringResource(R.string.previous_month),
                tint = LocalTBCColors.current.onBackground,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onPrevMonth)
            )

            Icon(
                painter = painterResource(R.drawable.right_arrow_svgrepo_com),
                contentDescription = stringResource(R.string.next_month),
                tint = LocalTBCColors.current.onBackground,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onNextMonth)
            )
        }
    }
}

