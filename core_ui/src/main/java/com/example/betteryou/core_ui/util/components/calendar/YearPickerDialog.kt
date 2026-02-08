package com.example.betteryou.core_ui.util.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.components.TBCAppButton
import java.time.LocalDate

@Composable
fun YearPickerDialog(
    currentYear: Int,
    onYearSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    startYear: Int = currentYear - 100,
    endYear: Int = LocalDate.now().year
) {
    var selectedYear by remember { mutableStateOf(currentYear) }
    val years = (endYear downTo startYear).toList()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = Radius.radius24,
            color = LocalTBCColors.current.surface,
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select year",
                    style = LocalTBCTypography.current.bodyLarge,
                    color = LocalTBCColors.current.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    years.forEach { year ->
                        val isSelected = year == selectedYear
                        Text(
                            text = year.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { selectedYear = year }
                                .background(
                                    if (isSelected) LocalTBCColors.current.background else androidx.compose.ui.graphics.Color.Transparent,
                                    Radius.radius12
                                )
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            style = LocalTBCTypography.current.bodyMedium,
                            color = if (isSelected) LocalTBCColors.current.accent else LocalTBCColors.current.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TBCAppButton(
                    text = "Save",
                    onClick = {
                        onYearSelected(selectedYear)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


