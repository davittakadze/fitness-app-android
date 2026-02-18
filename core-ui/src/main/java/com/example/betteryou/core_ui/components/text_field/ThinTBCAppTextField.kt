package com.example.betteryou.core_ui.components.text_field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Radius

@Composable
fun ThinTBCAppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    numbersOnly: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    val backgroundColor =
        if (!isFocused)
            LocalTBCColors.current.surface.copy(alpha = 0.6f)
        else
            LocalTBCColors.current.surface

    val borderColor =
        if (isFocused)
            LocalTBCColors.current.accent
        else
            LocalTBCColors.current.avatarBorder


    BasicTextField(
        value = value,
        onValueChange = { input ->
            val filtered = if (numbersOnly) {
                val digitsAndDot = input.filter { it.isDigit() || it == '.' }
                val firstDotIndex = digitsAndDot.indexOfFirst { it == '.' }
                if (firstDotIndex >= 0) {
                    digitsAndDot.take(firstDotIndex + 1) + digitsAndDot.drop(firstDotIndex + 1).replace(".", "")
                } else {
                    digitsAndDot
                }
            } else input
            onValueChange(filtered)
        },
        enabled = enabled,
        singleLine = true,
        textStyle = LocalTBCTypography.current.bodyLarge.copy(
            color = LocalTBCColors.current.onBackground
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .onFocusChanged { isFocused = it.isFocused },
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = backgroundColor,
                        shape = Radius.radius12
                    )
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = Radius.radius12
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    )
            ) {

                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = LocalTBCTypography.current.bodyLarge,
                            color = LocalTBCColors.current.textSecondary
                        )
                    }
                    innerTextField()
                }

                if (!isFocused) {
                    Icon(
                        painter = painterResource(R.drawable.pencil_edit_button_svgrepo_com),
                        contentDescription = null,
                        tint = LocalTBCColors.current.textSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    )
}

