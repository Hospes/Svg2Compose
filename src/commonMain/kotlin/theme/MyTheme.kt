package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*

val LocalMyColor: ProvidableCompositionLocal<MyColorsScheme> =
    staticCompositionLocalOf { MyLightColorsScheme/*error("no provided")*/ }

val LocalMyTypography: ProvidableCompositionLocal<MyTypography> =
    staticCompositionLocalOf { MyTypography }

val LocalMyShapes: ProvidableCompositionLocal<MyShapes> =
    staticCompositionLocalOf { MyShapes }

object MyTheme {
    val colors: MyColorsScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalMyColor.current
    val typography: MyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMyTypography.current
    val shapes: MyShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalMyShapes.current
}

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val localMyColors: MyColorsScheme = if (darkTheme) MyDarkColorsScheme else MyLightColorsScheme

    CompositionLocalProvider(
        LocalMyColor provides localMyColors,
        LocalMyTypography provides MyTypography,
        LocalMyShapes provides MyShapes,
    ) {
        MaterialTheme(
            colors = LocalMyColor.current.asMaterialColors(),
            typography = LocalMyTypography.current.asMaterialTypography(),
            shapes = LocalMyShapes.current.asMaterialShapes(),
        ) {
            val selectionColors = LocalTextSelectionColors.current
            val newSelectionColors = remember(selectionColors) {
                TextSelectionColors(
                    handleColor = localMyColors.tertiary,
                    backgroundColor = selectionColors.backgroundColor
                )
            }
            CompositionLocalProvider(
                LocalTextSelectionColors provides newSelectionColors,
                content = content,
            )
        }
    }
}