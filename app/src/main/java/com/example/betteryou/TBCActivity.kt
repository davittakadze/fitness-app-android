package com.example.betteryou

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.navigation.TBCAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TBCActivity : AppCompatActivity() {
    private val appViewModel: TBCAppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TBCTheme(darkTheme = appViewModel.isDarkTheme) {
                TBCAppTheme()
            }
        }
    }
}
