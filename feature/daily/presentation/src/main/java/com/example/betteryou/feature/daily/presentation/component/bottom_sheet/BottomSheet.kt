package com.example.betteryou.feature.daily.presentation.component.bottom_sheet

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.components.button.AppButtonType
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.daily.presentation.model.ProductUi

@Composable
fun BottomSheet(
    item: ProductUi,
    onClose: () -> Unit,
    addProductQuantity: (quantity: Double, product: ProductUi) -> Unit,
) {
    var quantity by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(600.dp)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }) {

        TBCAppAsyncImage(
            img = item.photo,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            placeholder = R.drawable.icon_workout_screen
        )

        Spacer(modifier = Modifier.height(Spacer.spacing12))

        Column {
            Text(
                text = item.name,
                style = TBCTheme.typography.headlineLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(Spacer.spacing16))

            Text(
                text = item.description,
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(Spacer.spacing32))

            Text(
                text = stringResource(R.string.per_100g),
                style = TBCTheme.typography.headlineLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(Spacer.spacing16))

            Text(
                text = stringResource(R.string.calories_intake, item.calories),
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(Spacer.spacing8))

            Text(
                text = stringResource(R.string.protein_intake, item.protein),
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(Spacer.spacing8))

            Text(
                text = stringResource(R.string.fats_intake, item.fat),
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(Spacer.spacing8))

            Text(
                text = stringResource(R.string.carbs_intake, item.carbs),
                style = TBCTheme.typography.bodyLarge,
                color = TBCTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        Spacer(modifier = Modifier.height(Spacer.spacing16))
        Row(Modifier.padding(horizontal = 24.dp)) {
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TBCAppTextField(
                    value = quantity,
                    onValueChange = { input ->
                        if (input.matches(Regex("^\\d*\\.?\\d*$"))) {
                            quantity = input
                        }
                    },
                    placeholder = "100",
                    keyboardType = KeyboardType.Number,
                    numbersOnly = true,
                    modifier = Modifier
                        .size(width = 82.dp, height = 52.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.g),
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground,
                    modifier = Modifier.padding(end = 24.dp, top = 24.dp, bottom = 24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(Spacer.spacing16))
        TBCAppButton(
            text = stringResource(R.string.add),
            onClick = {
                val amount: Double = quantity.toDoubleOrNull() ?: 0.0
                if (amount > 0) {
                    addProductQuantity(amount, item)
                }
                onClose()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(52.dp),
            type = AppButtonType.Outlined
        )
        Spacer(modifier = Modifier.height(Spacer.spacing16))
    }
}