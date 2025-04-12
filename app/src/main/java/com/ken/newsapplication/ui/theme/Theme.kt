package com.ken.newsapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = Black,
    primary = Blue,
    error = DarkRed,
    surface = LightBlack
)

private val LightColorScheme = lightColorScheme(
    background = Color.White,
    primary = Blue,
    error = LightRed,
    surface = Color.White
)

@Composable
fun NewsApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            /* val window = (view.context as Activity).window
             window.statusBarColor = colorScheme.primary.toArgb()
             WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
         */
            try {
                val window = (view.context as? Activity)?.window
                window?.let { win ->
                    val insetsController = WindowCompat.getInsetsController(win, view)
                    // Set appearance of status bar
                    insetsController.isAppearanceLightStatusBars = !darkTheme

                    // For background color, use window background with transparent status bar
                    WindowCompat.setDecorFitsSystemWindows(win, false)
                    win.setBackgroundDrawableResource(android.R.color.transparent)
                    // Note: Actual status bar color is now controlled by the surface color
                    // in your layout, not directly set here
                }
            } catch (e: Exception) {
                // Handle potential window modification failures gracefully
            }

        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}