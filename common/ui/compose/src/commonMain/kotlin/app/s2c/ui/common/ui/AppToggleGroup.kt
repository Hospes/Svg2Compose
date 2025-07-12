@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package app.s2c.ui.common.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ToggleButtonShapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun AppToggleGroup(
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.extraExtraLarge,
    content: @Composable AppToggleGroupScope.() -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        val leftButtonShape = remember(shape) {
            RoundedCornerShape(topStart = shape.topStart, topEnd = ZeroCornerSize, bottomStart = shape.bottomStart, bottomEnd = ZeroCornerSize)
        }
        val rightButtonShape = remember(shape) {
            RoundedCornerShape(topStart = ZeroCornerSize, topEnd = shape.topEnd, bottomStart = ZeroCornerSize, bottomEnd = shape.bottomEnd)
        }
        val leftButtonShapes = remember(leftButtonShape) {
            ToggleButtonShapes(shape = leftButtonShape, pressedShape = leftButtonShape, checkedShape = leftButtonShape)
        }
        val rightButtonShapes = remember(rightButtonShape) {
            ToggleButtonShapes(shape = rightButtonShape, pressedShape = rightButtonShape, checkedShape = rightButtonShape)
        }
        val middleButtonShape = remember { RectangleShape }
        val middleButtonShapes = remember(middleButtonShape) {
            ToggleButtonShapes(shape = middleButtonShape, pressedShape = middleButtonShape, checkedShape = middleButtonShape)
        }

        val scope = remember {
            AppToggleGroupScopeImpl(
                rowScope = this,
                onFirstShapes = { leftButtonShapes },
                onLastShapes = { rightButtonShapes },
                onMiddleShapes = { middleButtonShapes },
            )
        }
        content(scope)
    }
}

@Stable
interface AppToggleGroupScope : RowScope {
    val firstShapes: ToggleButtonShapes
    val lastShapes: ToggleButtonShapes
    val middleShapes: ToggleButtonShapes
}

private class AppToggleGroupScopeImpl(
    private val rowScope: RowScope,
    val onFirstShapes: () -> ToggleButtonShapes,
    val onLastShapes: () -> ToggleButtonShapes,
    val onMiddleShapes: () -> ToggleButtonShapes,
) : AppToggleGroupScope, RowScope by rowScope {
    override val firstShapes: ToggleButtonShapes get() = onFirstShapes()
    override val lastShapes: ToggleButtonShapes get() = onLastShapes()
    override val middleShapes: ToggleButtonShapes get() = onMiddleShapes()
}