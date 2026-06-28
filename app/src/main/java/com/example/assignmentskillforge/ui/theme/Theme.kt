package com.example.assignmentskillforge.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Fixed light color scheme — always uses app's teal brand palette
// dynamicColor is intentionally DISABLED so device wallpaper doesn't
// override our carefully chosen colors on Android 12+
private val AppColorScheme = lightColorScheme(
    primary          = BrandTeal,
    onPrimary        = Color.White,
    primaryContainer = BrandTealLight,
    onPrimaryContainer = Color.White,

    secondary        = BrandTealDark,
    onSecondary      = Color.White,

    background       = AppBackground,
    onBackground     = TextPrimary,

    surface          = SurfaceWhite,
    onSurface        = TextPrimary,

    surfaceVariant   = AppBackground,
    onSurfaceVariant = TextSecondary,

    outline          = DividerColor,
)

@Composable
fun AssignmentSkillforgeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography  = Typography,
        content     = content
    )
}