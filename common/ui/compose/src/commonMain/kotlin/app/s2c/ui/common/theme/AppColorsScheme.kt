package app.s2c.ui.common.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

interface AppColorsScheme {
    val primary: Color
    val onPrimary: Color
    val primaryContainer: Color
    val onPrimaryContainer: Color

    val secondary: Color
    val onSecondary: Color
    val secondaryContainer: Color
    val onSecondaryContainer: Color

    val tertiary: Color
    val onTertiary: Color
    val tertiaryContainer: Color
    val onTertiaryContainer: Color

    val background: Color
    val onBackground: Color

    val surface: Color
    val surfaceDim: Color
    val surfaceBright: Color
    val surfaceContainer: Color
    val surfaceContainerLow: Color
    val onSurface: Color
    val onSurfaceVariant: Color

    val error: Color
    val onError: Color

    val outline: Color
    val outlineVariant: Color
    val scrim: Color

    val even: Color
    val odd: Color
    val link: Color

    val isLight: Boolean

    fun asMaterialColors(): ColorScheme = darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = primary,

        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,

        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,

        background = background,
        onBackground = onBackground,

        surface = surface,
        surfaceDim = surfaceDim,
        surfaceBright = surfaceBright,
        onSurface = onSurface,
        surfaceVariant = surface,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surface,
        inverseSurface = surface,
        inverseOnSurface = onSurface,

        error = error,
        onError = onError,
        errorContainer = error,
        onErrorContainer = onError,

        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,

        surfaceContainer = surfaceContainer,
        surfaceContainerHigh = surface,
        surfaceContainerHighest = surface,
        surfaceContainerLow = surfaceContainerLow,
        surfaceContainerLowest = surface,
    )
}


// Light theme
object AppLightColorsScheme : AppColorsScheme {
    override val primary: Color = AppColorPalette.Brand500
    override val onPrimary: Color = AppColorPalette.White
    override val primaryContainer: Color = AppColorPalette.Flavour100
    override val onPrimaryContainer: Color = primary

    override val secondary: Color = AppColorPalette.Attention500
    override val onSecondary: Color = AppColorPalette.White
    override val secondaryContainer: Color = AppColorPalette.Attention50
    override val onSecondaryContainer: Color = secondary

    override val tertiary: Color = AppColorPalette.Curious500
    override val onTertiary: Color = AppColorPalette.White
    override val tertiaryContainer: Color = AppColorPalette.Curious50
    override val onTertiaryContainer: Color = tertiary

    override val background: Color = AppColorPalette.White
    override val onBackground: Color = AppColorPalette.Black

    override val surface: Color = Color(0xFF121814)
    override val surfaceDim: Color = Color(0xFF101410)
    override val surfaceBright: Color = Color(0xFF383C38)
    override val surfaceContainer: Color = Color(0xFF242B26)
    override val surfaceContainerLow: Color = Color(0xFF1F2621)
    override val onSurface: Color = Color(0xFFE3E3E3)
    override val onSurfaceVariant: Color = Color(0xFFBFC9BF)

    override val error: Color = AppColorPalette.Attention500
    override val onError: Color = AppColorPalette.White

    override val outline: Color = AppColorPalette.Grey500
    override val outlineVariant: Color = AppColorPalette.Grey300
    override val scrim: Color = AppColorPalette.Black

    override val even: Color = Color.Transparent
    override val odd: Color = AppColorPalette.Grey50
    override val link: Color = primary
    override val isLight: Boolean = true
}

// Dark theme
object AppDarkColorsScheme : AppColorsScheme {
    override val primary: Color = Color(0xFF4CAF50)
    override val onPrimary: Color = Color(0xFFFFFFFF)
    override val primaryContainer: Color = Color(0xFF2E7D32)
    override val onPrimaryContainer: Color = Color(0xFFC8E6C9)

    override val secondary: Color = AppColorPalette.Attention500
    override val onSecondary: Color = AppColorPalette.White
    override val secondaryContainer: Color = Color(0xFF388E3C)
    override val onSecondaryContainer: Color = Color(0xFFE8F5E9)

    override val tertiary: Color = AppColorPalette.Curious500
    override val onTertiary: Color = AppColorPalette.White
    override val tertiaryContainer: Color = AppColorPalette.Curious50
    override val onTertiaryContainer: Color = tertiary

    override val background: Color = Color(0xFF121814)
    override val onBackground: Color = AppColorPalette.White

    override val surface: Color = Color(0xFF121814)
    override val surfaceDim: Color = Color(0xFF101410)
    override val surfaceBright: Color = Color(0xFF383C38)
    override val surfaceContainer: Color = Color(0xFF242B26)
    override val surfaceContainerLow: Color = Color(0xFF1F2621)
    override val onSurface: Color = Color(0xFFE3E3E3)
    override val onSurfaceVariant: Color = Color(0xFFBFC9BF)

    override val error: Color = AppColorPalette.Attention500
    override val onError: Color = AppColorPalette.White

    override val outline: Color = Color(0xFF434943)
    override val outlineVariant: Color = AppColorPalette.Grey300
    override val scrim: Color = AppColorPalette.Black

    override val even: Color = Color.Transparent
    override val odd: Color = AppColorPalette.Grey50
    override val link: Color = primary
    override val isLight: Boolean = true
}