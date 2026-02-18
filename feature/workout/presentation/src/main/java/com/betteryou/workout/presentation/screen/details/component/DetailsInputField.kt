package com.betteryou.workout.presentation.screen.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Radius
import kotlin.text.isDigit

@Composable
fun DetailsInputField(value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = { input ->
            val digitsAndDot = input.filter { it.isDigit() || it == '.' }
            val firstDotIndex = digitsAndDot.indexOfFirst { it == '.' }
            val filtered = if (firstDotIndex >= 0) {
                digitsAndDot.take(firstDotIndex + 1) + digitsAndDot.drop(firstDotIndex + 1)
                    .replace(".", "")
            } else {
                digitsAndDot
            }
            onValueChange(filtered)
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(
                width = 1.dp,
                color = TBCTheme.colors.border,
                shape = Radius.radius8
            )
            .background(TBCTheme.colors.onPrimary, Radius.radius8)
            .padding(top = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}