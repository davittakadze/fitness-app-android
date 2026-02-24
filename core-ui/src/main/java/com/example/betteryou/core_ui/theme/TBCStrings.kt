@file:Suppress("DEPRECATION")

package com.example.betteryou.core_ui.theme

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

//@Composable
//fun TBCStrings(
//    isGeorgian: Boolean,
//    content: @Composable () -> Unit
//) {
//    val context = LocalContext.current
//    val locale = if (isGeorgian) Locale("ka") else Locale("en")
//
//    CompositionLocalProvider(
//        LocalContext provides context.createConfigurationContext(
//            Configuration(context.resources.configuration).apply {
//                setLocale(locale)
//            }
//        )
//    ) {
//        content()
//    }
//}