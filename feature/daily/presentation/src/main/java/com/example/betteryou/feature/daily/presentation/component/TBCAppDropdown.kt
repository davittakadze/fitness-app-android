package com.example.betteryou.feature.daily.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.daily.presentation.model.ProductUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TBCAppDropdown(
    items: List<ProductUi>,
    onItemSelected: (ProductUi) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }

    val filteredItems = remember(query, items) {
        if (query.isEmpty()) items
        else items.filter { it.name.contains(query, ignoreCase = true) }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clip(Radius.radius12)
    ) {
        TBCAppTextField(
            value = query,
            onValueChange = {
                query = it
                expanded = true
            },
            placeholder = stringResource(R.string.select_product),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                query = ""
            },
            modifier = Modifier
                .background(TBCTheme.colors.background, shape = Radius.radius12)
                .clip(Radius.radius12)
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = { TBCAppDropdownItem(item) }, onClick = {
                        onItemSelected(item)
                        query = ""
                        expanded = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}