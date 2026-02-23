package com.example.betteryou.feature.profile.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betteryou.feature.profile.presentation.model.Sex

@Composable
fun SexSelector(
    selected: Sex?,
    onSelected: (Sex) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SexItem(
            text = "Male",
            selected = selected == Sex.MALE,
            onClick = { onSelected(Sex.MALE) },
            modifier = Modifier.weight(1f)
        )

        SexItem(
            text = "Female",
            selected = selected == Sex.FEMALE,
            onClick = { onSelected(Sex.FEMALE) },
            modifier = Modifier.weight(1f)
        )
    }
}
