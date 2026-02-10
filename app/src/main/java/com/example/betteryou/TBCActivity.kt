package com.example.betteryou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.navigation.TBCAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TBCActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appViewModel: TBCAppViewModel = viewModel()
            TBCTheme(darkTheme = appViewModel.isDarkTheme) {
                TBCAppTheme()
            }
        }
    }
}