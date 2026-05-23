package com.henriquesebastiao.helpos.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val Primary = Color(0xFF2E6BE6)
private val OnPrimary = Color(0xFFFFFFFF)
private val PrimaryContainer = Color(0xFFD8E2FF)
private val OnPrimaryContainer = Color(0xFF001A41)

private val Secondary = Color(0xFF565F71)
private val OnSecondary = Color(0xFFFFFFFF)
private val SecondaryContainer = Color(0xFFDAE2F9)
private val OnSecondaryContainer = Color(0xFF131C2B)

private val Tertiary = Color(0xFF705575)
private val OnTertiary = Color(0xFFFFFFFF)
private val TertiaryContainer = Color(0xFFFAD8FD)
private val OnTertiaryContainer = Color(0xFF28132E)

private val Error = Color(0xFFBA1A1A)
private val OnError = Color(0xFFFFFFFF)
private val ErrorContainer = Color(0xFFFFDAD6)
private val OnErrorContainer = Color(0xFF410002)

private val Background = Color(0xFFFAF9FD)
private val OnBackground = Color(0xFF1A1B20)
private val Surface = Color(0xFFFAF9FD)
private val OnSurface = Color(0xFF1A1B20)
private val SurfaceVariant = Color(0xFFE1E2EC)
private val OnSurfaceVariant = Color(0xFF44464F)
private val Outline = Color(0xFF74777F)

private val DarkPrimary = Color(0xFFADC6FF)
private val DarkOnPrimary = Color(0xFF002E69)
private val DarkPrimaryContainer = Color(0xFF124493)
private val DarkOnPrimaryContainer = Color(0xFFD8E2FF)

private val DarkSecondary = Color(0xFFBEC6DC)
private val DarkOnSecondary = Color(0xFF283041)
private val DarkSecondaryContainer = Color(0xFF3E4759)
private val DarkOnSecondaryContainer = Color(0xFFDAE2F9)

private val DarkTertiary = Color(0xFFDDBCE0)
private val DarkOnTertiary = Color(0xFF3F2845)
private val DarkTertiaryContainer = Color(0xFF573E5C)
private val DarkOnTertiaryContainer = Color(0xFFFAD8FD)

private val DarkError = Color(0xFFFFB4AB)
private val DarkOnError = Color(0xFF690005)
private val DarkErrorContainer = Color(0xFF93000A)
private val DarkOnErrorContainer = Color(0xFFFFDAD6)

private val DarkBackground = Color(0xFF111318)
private val DarkOnBackground = Color(0xFFE2E2E9)
private val DarkSurface = Color(0xFF111318)
private val DarkOnSurface = Color(0xFFE2E2E9)
private val DarkSurfaceVariant = Color(0xFF44464F)
private val DarkOnSurfaceVariant = Color(0xFFC4C6D0)
private val DarkOutline = Color(0xFF8E9099)

internal val LightFallbackColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
)

internal val DarkFallbackColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
)
