package com.example.betteryou.core_ui.util.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.betteryou.core_res.R

@Composable
fun AppAsyncImage(
    model: Any?,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = model,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        error = painterResource(R.drawable.ic_launcher_background),
        fallback = painterResource(R.drawable.ic_launcher_background)
    )
}
