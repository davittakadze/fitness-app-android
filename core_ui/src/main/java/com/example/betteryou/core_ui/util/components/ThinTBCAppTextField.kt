package com.example.betteryou.core_ui.util.components

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
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius

@Composable
fun ThinTBCAppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    val backgroundColor =
        if (!isFocused && value.isEmpty())
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
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        textStyle = LocalTBCTypography.current.bodyLarge.copy(
            color = LocalTBCColors.current.onBackground
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
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
                    .padding(horizontal = 12.dp)
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

                if (!isFocused && value.isEmpty()) {
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
