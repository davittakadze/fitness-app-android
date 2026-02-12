package com.example.betteryou.core_ui.util.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import java.time.Month
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.components.TBCAppButton

@Composable
fun MonthPicker(
    currentMonth: Int,
    onMonthSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedMonth by remember { mutableStateOf(currentMonth) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = Radius.radius24,
            color = LocalTBCColors.current.background,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(350.dp)
            ) {
                // Title
                Text(
                    text = stringResource(R.string.select_month),
                    style = LocalTBCTypography.current.bodyLarge,
                    color = LocalTBCColors.current.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    (1..12).forEach { month ->
                        val isSelected = month == selectedMonth
                        Text(
                            text = Month.of(month)
                                .name
                                .lowercase()
                                .replaceFirstChar { it.uppercase() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(Radius.radius12)
                                .clickable { selectedMonth = month }
                                .background(
                                    if (isSelected) LocalTBCColors.current.surface
                                    else Color.Transparent
                                )
                                .padding(vertical = 14.dp, horizontal = 12.dp),
                            style = LocalTBCTypography.current.bodyMedium,
                            color = if (isSelected) LocalTBCColors.current.accent
                            else LocalTBCColors.current.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TBCAppButton(
                    text = "Save",
                    onClick = {
                        onMonthSelected(selectedMonth)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


