package com.example.betteryou.core_ui.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Radius

@Composable
fun TBCAppSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val colors = TBCTheme.colors

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        singleLine = true,
        textStyle = TBCTheme.typography.bodyLarge.copy(color = colors.textPrimary),
        cursorBrush = SolidColor(colors.accent),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colors.background , Radius.radius16)
            .border(
                width = if (isFocused) 1.5.dp else 1.dp,
                color = if (isFocused) colors.accent else colors.border,
                shape = Radius.radius16
            ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = if (isFocused) colors.accent else colors.textSecondary,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TBCTheme.typography.bodyLarge,
                            color = colors.textSecondary
                        )
                    }
                    innerTextField()
                }

                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = { onValueChange("") },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Clear",
                            tint = colors.textSecondary
                        )
                    }
                }
            }
        }
    )
}


