package com.example.betteryou.core_ui.components.text_field

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Radius

@Composable
fun TBCAppPasswordField(
    value: String,
    isPasswordVisible: Boolean,
    onPasswordChange: (String) -> Unit,
    onIconClick: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val colors = LocalTBCColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderColor = if (isFocused) {
        colors.accent
    } else {
        colors.border
    }
    Box(
        modifier = modifier
            .height(52.dp)
            .border(
                width = if(isFocused){
                    2.dp
                }else{
                    1.dp
                },
                color = borderColor,
                shape = Radius.radius12
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onPasswordChange,
            singleLine = true,
            visualTransformation =
                if (isPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
            textStyle = LocalTBCTypography.current.bodyLarge.copy(
                color = LocalTBCColors.current.textPrimary
            ),
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = LocalTBCTypography.current.bodyLarge,
                                color = LocalTBCColors.current.textSecondary
                            )
                        }
                        innerTextField()
                    }

                    IconButton(onClick = onIconClick) {
                        Icon(
                            painter = painterResource(
                                if (isPasswordVisible)
                                    R.drawable.eye_svgrepo_com
                                else
                                    R.drawable.eye_closed_svgrepo_com
                            ),
                            contentDescription = null,
                            tint = LocalTBCColors.current.textSecondary
                        )
                    }
                }
            }
        )
    }
}
