package com.example.homework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.homework3.navigation.TBCAppTheme
import com.example.homework3.ui.theme.TBCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TBCActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TBCTheme {
                TBCAppTheme()
            }
        }
    }
}