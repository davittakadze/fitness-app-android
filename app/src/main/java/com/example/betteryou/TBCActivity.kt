package com.example.betteryou

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.betteryou.core.notifications.NotificationHelper
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.domain.common.NotificationConfig
import com.example.betteryou.navigation.TBCAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TBCActivity : AppCompatActivity() {
    
    @Inject
    lateinit var notificationHelper: NotificationHelper

    @Inject
    lateinit var waterNotificationConfig: NotificationConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appViewModel: TBCAppViewModel = hiltViewModel()
            TBCTheme(darkTheme = appViewModel.isDarkTheme) {
                TBCAppTheme()
            }
        }
    }
}
