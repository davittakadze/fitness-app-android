package com.example.betteryou.core_ui.util.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import com.example.betteryou.core_ui.TBCTheme

@Composable
fun TBCAppAsyncImage(
    modifier: Modifier = Modifier,
    img: Any?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    @DrawableRes placeholder: Int,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = img,
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = {
            Box(
                modifier = Modifier
                    .background(TBCTheme.colors.surface)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize()
            )
        },
    )
}
