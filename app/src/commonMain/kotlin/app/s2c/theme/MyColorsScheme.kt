package app.s2c.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

interface MyColorsScheme {
    val primary: Color
    val primaryVariant: Color
    val primarySurface: Color
    val secondary: Color
    val secondaryVariant: Color
    val secondarySurface: Color
    val tertiary: Color
    val tertiaryVariant: Color
    val tertiarySurface: Color
    val background: Color
    val surface: Color
    val error: Color
    val onPrimary: Color
    val onPrimarySurface: Color
    val onSecondary: Color
    val onSecondarySurface: Color
    val onTertiary: Color
    val onTertiarySurface: Color
    val onBackground: Color
    val onSurface: Color
    val onError: Color

    val isLight: Boolean

    fun asMaterialColors(): Colors = Colors(
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        secondaryVariant = secondaryVariant,
        background = background,
        surface = surface,
        error = error,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onError = onError,
        isLight = isLight,
    )
}


// Light theme
object MyLightColorsScheme : MyColorsScheme {
    override val primary: Color = MyPalette.Brand500
    override val primaryVariant: Color = primary
    override val primarySurface: Color = MyPalette.Brand50
    override val secondary: Color = MyPalette.Attention500
    override val secondaryVariant: Color = secondary
    override val secondarySurface: Color = MyPalette.Attention50
    override val tertiary: Color = MyPalette.Curious500
    override val tertiaryVariant: Color = tertiary
    override val tertiarySurface: Color = MyPalette.Curious50
    override val background: Color = MyPalette.White
    override val surface: Color = background
    override val error: Color = MyPalette.Attention500
    override val onPrimary: Color = MyPalette.White
    override val onPrimarySurface: Color = MyPalette.Black
    override val onSecondary: Color = MyPalette.White
    override val onSecondarySurface: Color = MyPalette.Black
    override val onTertiary: Color = MyPalette.White
    override val onTertiarySurface: Color = MyPalette.Black
    override val onBackground: Color = MyPalette.Black
    override val onSurface: Color = onBackground
    override val onError: Color = MyPalette.White
    override val isLight: Boolean = true
}

// Dark theme
object MyDarkColorsScheme : MyColorsScheme {
    override val primary: Color = MyPalette.Brand500
    override val primaryVariant: Color = primary
    override val primarySurface: Color = MyPalette.Brand50
    override val secondary: Color = MyPalette.Attention500
    override val secondaryVariant: Color = secondary
    override val secondarySurface: Color = MyPalette.Attention50
    override val tertiary: Color = MyPalette.Curious500
    override val tertiaryVariant: Color = tertiary
    override val tertiarySurface: Color = MyPalette.Curious50
    override val background: Color = MyPalette.White
    override val surface: Color = background
    override val error: Color = MyPalette.Attention500
    override val onPrimary: Color = MyPalette.White
    override val onPrimarySurface: Color = MyPalette.Black
    override val onSecondary: Color = MyPalette.White
    override val onSecondarySurface: Color = MyPalette.Black
    override val onTertiary: Color = MyPalette.White
    override val onTertiarySurface: Color = MyPalette.Black
    override val onBackground: Color = MyPalette.Black
    override val onSurface: Color = onBackground
    override val onError: Color = MyPalette.White
    override val isLight: Boolean = true
}