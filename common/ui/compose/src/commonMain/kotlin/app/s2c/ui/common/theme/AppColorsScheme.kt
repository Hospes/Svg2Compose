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
    val onSurface: Color
    val surfaceVariant: Color
    val onSurfaceVariant: Color
    val surfaceTint: Color
    val inverseSurface: Color
    val inverseOnSurface: Color
    val surfaceDim: Color
    val surfaceBright: Color
    val surfaceContainer: Color
    val surfaceContainerLow: Color

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
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        surfaceTint = surfaceTint,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        surfaceDim = surfaceDim,
        surfaceBright = surfaceBright,

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
    // --- Primary Colors ---
    override val primary = Color(0xFF4CAF50) // A vibrant, accessible green for key components
    override val onPrimary = Color(0xFFFFFFFF) // White text/icons on primary color
    override val primaryContainer = Color(0xFF2E7D32) // A darker shade for components needing less emphasis
    override val onPrimaryContainer = Color(0xFFC8E6C9) // Light mint for text/icons on primaryContainer

    // --- Secondary Colors ---
    override val secondary = Color(0xFF66BB6A) // A less prominent accent color
    override val onSecondary = Color(0xFF003913) // Dark text/icons on secondary color
    override val secondaryContainer = Color(0xFF388E3C) // A container for secondary elements
    override val onSecondaryContainer = Color(0xFFE8F5E9) // Light text/icons on secondaryContainer

    // --- Tertiary Colors ---
    override val tertiary = Color(0xFFBCA136) // A contrasting accent color (e.g., for highlights, rewards)
    override val onTertiary = Color(0xFF402D00) // Dark text/icons on tertiary color
    override val tertiaryContainer = Color(0xFF5A4300) // A container for tertiary elements
    override val onTertiaryContainer = Color(0xFFFFDEA3) // Light text/icons on tertiaryContainer

    // --- Error Colors ---
    override val error = Color(0xFFF44336) // The standard color for errors
    override val onError = Color(0xFFFFFFFF) // White text/icons on error color
    val errorContainer = Color(0xFF93000A) // A container for less-prominent error states
    val onErrorContainer = Color(0xFFFFDAD6) // Light text/icons on errorContainer

    // --- Surface & Background Colors ---
    override val background = Color(0xFF101410) // The rearmost background color
    override val onBackground = Color(0xFFE3E3E3) // Text/icons on the background
    override val surface: Color = Color(0xFF121814) // The main surface color for components like cards, sheets
    override val surfaceVariant = Color(0xFF242B26) // A surface with a slightly different tint
    override val surfaceDim: Color = Color(0xFF101410)
    override val surfaceBright: Color = Color(0xFF383C38)
    override val surfaceTint = primary // A tint color applied to elevated surfaces, often the primary color
    override val surfaceContainer: Color = Color(0xFF242B26)
    override val surfaceContainerLow: Color = Color(0xFF1F2621)
    override val onSurface: Color = Color(0xFFE3E3E3)   // The default text/icon color on surfaces
    override val onSurfaceVariant: Color = Color(0xFFBFC9BF)    // A less-emphasized text/icon color

    // --- Inverse Colors (for creating inverted components) ---
    override val inverseSurface = Color(0xFFE3E3E3) // A light surface for components on a dark background
    override val inverseOnSurface = Color(0xFF1A201C) // A dark text color for an inverseSurface

    // --- Other Colors ---
    override val outline = Color(0xFF434943) // Color for component outlines (e.g., text fields)
    override val outlineVariant = Color(0xFF2D312D) // A less prominent outline
    override val scrim = Color(0xFF000000) // Color for screen overlays (e.g., behind dialogs)

    override val even: Color = Color.Transparent
    override val odd: Color = AppColorPalette.Grey50
    override val link: Color = primary
    override val isLight: Boolean = true
}

// Dark theme
object AppDarkColorsScheme : AppColorsScheme {
    // --- Primary Colors ---
    override val primary = Color(0xFF4CAF50) // A vibrant, accessible green for key components
    override val onPrimary = Color(0xFFFFFFFF) // White text/icons on primary color
    override val primaryContainer = Color(0xFF2E7D32) // A darker shade for components needing less emphasis
    override val onPrimaryContainer = Color(0xFFC8E6C9) // Light mint for text/icons on primaryContainer

    // --- Secondary Colors ---
    override val secondary = Color(0xFF66BB6A) // A less prominent accent color
    override val onSecondary = Color(0xFF003913) // Dark text/icons on secondary color
    override val secondaryContainer = Color(0xFF388E3C) // A container for secondary elements
    override val onSecondaryContainer = Color(0xFFE8F5E9) // Light text/icons on secondaryContainer

    // --- Tertiary Colors ---
    override val tertiary = Color(0xFFBCA136) // A contrasting accent color (e.g., for highlights, rewards)
    override val onTertiary = Color(0xFF402D00) // Dark text/icons on tertiary color
    override val tertiaryContainer = Color(0xFF5A4300) // A container for tertiary elements
    override val onTertiaryContainer = Color(0xFFFFDEA3) // Light text/icons on tertiaryContainer

    // --- Error Colors ---
    override val error = Color(0xFFF44336) // The standard color for errors
    override val onError = Color(0xFFFFFFFF) // White text/icons on error color
    val errorContainer = Color(0xFF93000A) // A container for less-prominent error states
    val onErrorContainer = Color(0xFFFFDAD6) // Light text/icons on errorContainer

    // --- Surface & Background Colors ---
    override val background = Color(0xFF101410) // The rearmost background color
    override val onBackground = Color(0xFFE3E3E3) // Text/icons on the background
    override val surface: Color = Color(0xFF121814) // The main surface color for components like cards, sheets
    override val surfaceVariant = Color(0xFF242B26) // A surface with a slightly different tint
    override val surfaceDim: Color = Color(0xFF101410)
    override val surfaceBright: Color = Color(0xFF383C38)
    override val surfaceTint = primary // A tint color applied to elevated surfaces, often the primary color
    override val surfaceContainer: Color = Color(0xFF242B26)
    override val surfaceContainerLow: Color = Color(0xFF1F2621)
    override val onSurface: Color = Color(0xFFE3E3E3)   // The default text/icon color on surfaces
    override val onSurfaceVariant: Color = Color(0xFFBFC9BF)    // A less-emphasized text/icon color

    // --- Inverse Colors (for creating inverted components) ---
    override val inverseSurface = Color(0xFFE3E3E3) // A light surface for components on a dark background
    override val inverseOnSurface = Color(0xFF1A201C) // A dark text color for an inverseSurface

    // --- Other Colors ---
    override val outline = Color(0xFF434943) // Color for component outlines (e.g., text fields)
    override val outlineVariant = Color(0xFF2D312D) // A less prominent outline
    override val scrim = Color(0xFF000000) // Color for screen overlays (e.g., behind dialogs)

    override val even: Color = Color.Transparent
    override val odd: Color = AppColorPalette.Grey50
    override val link: Color = primary
    override val isLight: Boolean = false
}