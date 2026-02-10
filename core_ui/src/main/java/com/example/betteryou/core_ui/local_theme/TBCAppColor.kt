package com.example.betteryou.core_ui.local_theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TBCAppColors(
    val background: Color,
    val onBackground:Color,
    val surface: Color,
    val primary: Color,
    val onPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val border: Color,
    val accent: Color,
    val avatarBorder:Color,
    val surfaceVariant: Color, // ბარათის შიდა ფერი
    val primaryContainer: Color, // მონიშნული ბარათის ფერი
    val onPrimaryContainer: Color, // ტექსტის ფერი მონიშნულ ბარათზე
    val outline: Color // ჩარჩოს ფერი
)

val LightAppColors = TBCAppColors(
    background = Color.White,
    onBackground = Color(0xFF111111),
    surface = Color(0xFFF8F8F8),
    primary = Color(0xFF111111),
    onPrimary = Color.White,
    textPrimary = Color(0xFF111111),
    textSecondary = Color(0xFF6E6E6E),
    border = Color(0xFFE0E0E0),
    accent = Color(0xFF6650a4),
    avatarBorder = Color(0xFFD6D6D6),
    surfaceVariant = Color(0xFFF5F5F5), // ღია ნაცრისფერი ბარათი
    primaryContainer = Color(0xFFEADDFF), // ღია იასამნისფერი (მონიშნულისთვის)
    onPrimaryContainer = Color(0xFF21005D), // მუქი იასამნისფერი ტექსტი
    outline = Color(0xFF79747E) // ნაცრისფერი ჩარჩო
)

val DarkAppColors = TBCAppColors(
    background = Color(0xFF1A2328),
    surface = Color(0xFF243238),
    primary = Color(0xFF2F3E46),
    onPrimary = Color(0xFFF2F6F7),
    onBackground = Color(0xFFF2F6F7),
    textPrimary = Color(0xFFF2F6F7),
    textSecondary = Color(0xFFB6C2C9),
    border = Color(0xFF3A4A52),
    accent = Color(0xFF4FB3BF),
    avatarBorder = Color(0xFF3A3A3A),
    surfaceVariant = Color(0xFF243238), // ოდნავ ღია ვიდრე background
    primaryContainer = Color(0xFF4F378B), // მუქი იასამნისფერი
    onPrimaryContainer = Color(0xFFEADDFF), // ღია იასამნისფერი ტექსტი
    outline = Color(0xFF938F99) // რუხი ჩარჩო
)

val LocalTBCColors = staticCompositionLocalOf<TBCAppColors> {
    error("TBCColors not provided")
}
