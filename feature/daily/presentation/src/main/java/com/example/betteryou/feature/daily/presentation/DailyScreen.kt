package com.example.betteryou.feature.daily.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.util.Spacer


@Composable
fun DailyScreen() {
    DailyScreenContent()
}

@Composable
fun DailyScreenContent() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column(Modifier
        .fillMaxSize()
        .background(LocalTBCColors.current.background)
        .padding(horizontal = 24.dp, vertical = 48.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.left_arrow_svgrepo_com),
            contentDescription = null,
            modifier = Modifier.padding(4.dp),
            tint = LocalTBCColors.current.onBackground
        )
        Spacer(Modifier.height(Spacer.spacing32))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when(page) {
                0 -> PageContent("Content of Page 1")
                1 -> PageContent("Content of Page 2")
                2 -> PageContent("Content of Page 3")
            }
        }
    }
}

@Composable
fun PageContent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = text, color = LocalTBCColors.current.onBackground)
    }
}

@Preview
@Composable
fun DailyScreenPreview() {
    TBCTheme {
        DailyScreenContent()
    }
}